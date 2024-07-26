package com.pro.resume.craft.fragments.cvdata.remove.background;

import static androidx.fragment.app.FragmentManager.TAG;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentRemoverBinding;
import com.pro.resume.craft.fragments.cvdata.personalinfo.PersonalInfoFragment;
import com.pro.resume.craft.models.DTOPersonalInfo;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.utils.ColorList;
import com.pro.resume.craft.utils.RemoveBackground;
import com.pro.resume.craft.utils.SharedPreferencesHelper;
import com.pro.resume.craft.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RemoverFragment extends Fragment {
    @Inject
    AppDatabase appDatabase;
    ArrayList<Integer> colorCodes;
    private FragmentRemoverBinding binding;
    private DTOProfile dtoProfile;
    private Bitmap outputBitmap;
    private int backgroundColor;
    private FirebaseFirestore db;
    private DTOPersonalInfo dtoPersonalInfo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRemoverBinding.inflate(inflater, container, false);
        colorCodes = new ArrayList<>();
        backgroundColor = ContextCompat.getColor(requireActivity(), R.color.color_f0f0f0);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId", "");
        DTOProfile profile = appDatabase.userDao().findByEmail(email);
        dtoProfile = appDatabase.userDao().findByEmail(email);
        dtoPersonalInfo = appDatabase.userDao().getPersonalInfo(email);

        colorCodes = ColorList.getColorList(requireActivity());

        if (dtoProfile == null) {
            if (profile != null) {
                Glide.with(requireContext()).load(profile.getProfilePhotoUrl()).into(binding.photo);
            }
        } else {
            Glide.with(requireContext()).load(dtoProfile.getProfilePhotoUrl()).into(binding.photo);
        }

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.submitText.getText().toString().equals("Submit")) {
                    Bitmap bitmap = getBitmapFromView();
                    replaceImage(bitmap);
                } else {
                    removeBackground();
                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(RemoverFragment.this).popBackStack();
            }
        });

        binding.bgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.bgColors.getVisibility() == View.GONE) {
                    binding.bgColors.setVisibility(View.VISIBLE);
                } else {
                    binding.bgColors.setVisibility(View.GONE);
                }
            }
        });

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.bgColors.setVisibility(View.GONE);
            }
        });

        ColorAdapter adapter = new ColorAdapter(requireActivity(), colorCodes, new ColorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                binding.photoBG.setCardBackgroundColor(colorCodes.get(position));
                binding.bgColors.setVisibility(View.GONE);
                backgroundColor = colorCodes.get(position);
            }
        });
        binding.recyclerView.setAdapter(adapter);
    }

    private void replaceImage(Bitmap bitmap) {
        // Get a reference to Firebase Storage
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        binding.progress.setVisibility(View.VISIBLE);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a reference to the path where the image is stored
        StorageReference imageRef = storage.getReference().child("profileImages").child(dtoProfile.getProfileId() + ".png");

        // Convert bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        // Upload the new image data to the same path
        UploadTask uploadTask = imageRef.putBytes(data);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Handle successful upload
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String newUrl = uri.toString();
                Log.d("TAG", "replaceImage: " + dtoProfile.getProfilePhotoUrl());
                Log.d("TAG", "replaceImage: " + newUrl);

                // The URL remains the same if you used the same path
                // Do something with the new URL if needed
                dtoProfile.setProfilePhotoUrl(newUrl);
                saveProfileToFirestore(dtoProfile, uid, dtoProfile.getProfileId());

                if (dtoPersonalInfo != null) {
                    dtoPersonalInfo = new DTOPersonalInfo(
                            dtoPersonalInfo.getId(),
                            dtoProfile.getFirstName(),
                            dtoProfile.getLastName(),
                            newUrl,
                            dtoProfile.getEmail(),
                            dtoProfile.getEmail(),
                            dtoPersonalInfo.getProfession(),
                            dtoPersonalInfo.getPhonenumber(),
                            dtoPersonalInfo.getAddress()
                    );
                } else {
                    dtoPersonalInfo = new DTOPersonalInfo(
                            0,
                            dtoProfile.getFirstName(),
                            dtoProfile.getLastName(),
                            newUrl,
                            dtoProfile.getEmail(),
                            dtoProfile.getEmail(),
                            "",
                            "",
                            ""
                    );
                }
                appDatabase.userDao().insertPersonalInfo(dtoPersonalInfo);

                dtoProfile = new DTOProfile(
                        dtoProfile.getId(),
                        dtoProfile.getProfileId(),
                        dtoProfile.getFirstName(),
                        dtoProfile.getLastName(),
                        newUrl,
                        dtoProfile.getEmail()
                );
                appDatabase.userDao().insert(dtoProfile);
            });
        }).addOnFailureListener(exception -> {
            exception.printStackTrace();
        });
    }

    private void removeBackground() {
        Bitmap inputBitmap = getBitmapFromImageView(binding.photo);
        RemoveBackground.Companion.removeBG(inputBitmap, binding.photo, binding.progress, new RemoveBackground.Companion.BackgroundRemovalCallback() {
            @Override
            public void onSuccess(@NonNull Bitmap bitmap) {
                outputBitmap = bitmap;
            }

            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(requireActivity(), "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.submitText.setText("Submit");
    }

    private void saveProfileToFirestore(DTOProfile profile, String uid, String profileId) {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(uid)
                .collection("profiles")
                .document(profileId)
                .set(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Utils.disableInteraction(true, requireActivity());
                            binding.progress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(RemoverFragment.this).popBackStack();
                        } else {
                            Utils.disableInteraction(true, requireActivity());
                            binding.progress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Error updating profile: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public Bitmap getBitmapFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            // Handle the case where the drawable is not a BitmapDrawable
            return null;
        }
    }

    private Bitmap getBitmapFromView() {
        // Set the background color to transparent
        binding.photo.setBackgroundColor(Color.TRANSPARENT);
        binding.photoBG.setDrawingCacheEnabled(true);
        binding.photoBG.buildDrawingCache();

        // Create a bitmap from the drawing cache
        Bitmap bitmap = Bitmap.createBitmap(binding.photoBG.getDrawingCache());

        // Disable drawing cache
        binding.photoBG.setDrawingCacheEnabled(false);

        return bitmap;
    }
}
