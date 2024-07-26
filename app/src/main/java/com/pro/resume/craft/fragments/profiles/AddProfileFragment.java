package com.pro.resume.craft.fragments.profiles;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentAddProfileBinding;
import com.pro.resume.craft.fragments.cvdata.remove.background.RemoverFragment;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.utils.Utils;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddProfileFragment extends Fragment {

    private FragmentAddProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Uri profileImageUri;

    @Inject
    AppDatabase appDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddProfileBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        binding.cvImgSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddProfileFragment.this)
                        .crop()                    // Crop image(Optional), Check Customization for more option
                        .compress(1024)            // Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080) // Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = Objects.requireNonNull(binding.firstNameText.getText()).toString().trim();
                String lastName = Objects.requireNonNull(binding.lastNameText.getText()).toString().trim();
                String email = Objects.requireNonNull(binding.userEmailText.getText()).toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    binding.firstNameInput.setError("First Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    binding.lastNameInput.setError("Last Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    binding.userEmailInput.setError("Email is required.");
                    return;
                }

                if (profileImageUri == null) {
                    Toast.makeText(getActivity(), "Profile photo is required.", Toast.LENGTH_SHORT).show();
                    return;
                }


                uploadImageAndSaveProfile(firstName, lastName,email);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddProfileFragment.this).popBackStack();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            profileImageUri = data.getData();
            binding.profilePhoto.setImageURI(profileImageUri);
        }
    }

    private void uploadImageAndSaveProfile(final String firstName, final String lastName,String email) {
        Utils.disableInteraction(false,requireActivity());
        binding.progressBar.setVisibility(View.VISIBLE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Utils.disableInteraction(true,requireActivity());
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "User not authenticated.", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        final String profileId = firstName + String.valueOf(System.currentTimeMillis());
        StorageReference profileImageRef = storage.getReference().child("profileImages").child(profileId + ".jpg");

        profileImageRef.putFile(profileImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String profilePhotoUrl = uri.toString();
                                DTOProfile profile = new DTOProfile(0,profileId, firstName, lastName, profilePhotoUrl,email);
                                saveProfileToFirestore(profile, uid, profileId);
                                DTOProfile profile1 = appDatabase.userDao().findByEmail(email);
                                if (profile1 == null){

                                    appDatabase.userDao().insert(profile);
                                }else{
                                    Toast.makeText(requireContext(),"Profile with this email already exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.disableInteraction(true,requireActivity());
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveProfileToFirestore(DTOProfile profile, String uid, String profileId) {
        db.collection("users")
                .document(uid)
                .collection("profiles")
                .document(profileId)
                .set(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Utils.disableInteraction(true,requireActivity());
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Profile added successfully.", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(requireView()).navigateUp();
                        } else {
                            Utils.disableInteraction(true,requireActivity());
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Error adding profile: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
