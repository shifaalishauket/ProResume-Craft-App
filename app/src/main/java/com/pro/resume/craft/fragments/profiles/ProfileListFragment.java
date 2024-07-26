package com.pro.resume.craft.fragments.profiles;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileListFragment extends Fragment {

    private FragmentProfileListBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<DTOProfile> profileList;
    private ProfileAdapter profileAdapter;

    @Inject
    AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        profileList = new ArrayList<>();
        profileAdapter = new ProfileAdapter(requireContext(), profileList, profile -> {
           createProfileOptionsBottomSheet(requireContext(),profile,mAuth,db,appDatabase);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(profileAdapter);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.addProfileFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
    }

    private void showBottomSheet(String profileId) {
        ProfileOptionsBottomSheet bottomSheet = new ProfileOptionsBottomSheet();
        bottomSheet.setProfileId(profileId); // Set profile ID to bottom sheet
        bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
    }

    private void fetchProfiles() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Handle the case where user is not authenticated
            return;
        }

        String uid = currentUser.getUid();
        db.collection("users").document(uid).collection("profiles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            profileList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                DTOProfile profile = document.toObject(DTOProfile.class);
                                DTOProfile profile1 = appDatabase.userDao().findByEmail(profile.getEmail());

                                if (profile1 == null){
                                    appDatabase.userDao().insert(profile);
                                }

                                if (profile != null) {
                                    profileList.add(profile);
                                }
                            }
                            if (profileList.isEmpty()){
                                binding.noDataText.setVisibility(View.VISIBLE);
                                binding.noDataView.setVisibility(View.VISIBLE);
                            }else{
                                binding.noDataText.setVisibility(View.GONE);
                                binding.noDataView.setVisibility(View.GONE);
                            }
                            profileAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void createProfileOptionsBottomSheet(Context context, DTOProfile profileId, FirebaseAuth mAuth, FirebaseFirestore db, AppDatabase appDatabase) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_options_sheet, null);

        TextView launchProfileTextView = view.findViewById(R.id.launchProfile);
        TextView deleteProfileTextView = view.findViewById(R.id.deleteProfile);

        launchProfileTextView.setOnClickListener(v -> {
            // Save profile ID to SharedPreferences using SharedPreferencesHelper
            SharedPreferencesHelper.saveString(context, "currentProfileId", profileId.getEmail());

            // Navigate to HomeFragment or perform any other action
            Toast.makeText(context, "Profile launched and saved!", Toast.LENGTH_SHORT).show();
            // Replace with actual navigation code if needed
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.homeFragment);
        });

        deleteProfileTextView.setOnClickListener(v -> showCustomDialog(context, profileId, mAuth, db, appDatabase));

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void showCustomDialog(Context context, DTOProfile profileId, FirebaseAuth mAuth, FirebaseFirestore db, AppDatabase appDatabase) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_confirm_delete, null);

        // Create the AlertDialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        // Get references to the views in the custom layout
        TextView dismiss = dialogView.findViewById(R.id.dismiss);
        TextView delete = dialogView.findViewById(R.id.delete);

        // Set the click listener for the dismiss button
        dismiss.setOnClickListener(v -> alertDialog.dismiss());

        // Set the click listener for the delete button
        delete.setOnClickListener(v -> {
            deleteProfile(context, profileId, mAuth, db, appDatabase);
            alertDialog.dismiss();
        });

        // Show the dialog
        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void deleteProfile(Context context, DTOProfile profileId, FirebaseAuth mAuth, FirebaseFirestore db, AppDatabase appDatabase) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        db.collection("users").document(uid).collection("profiles").document(profileId.getProfileId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Delete from SQLite
                    appDatabase.userDao().deleteProfileById(profileId.getEmail());

                    fetchProfiles();

                    Toast.makeText(context, "Profile deleted!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete profile", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchProfiles();
    }
}
