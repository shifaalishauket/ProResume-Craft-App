package com.pro.resume.craft.fragments.profiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileOptionsBottomSheet extends BottomSheetDialogFragment {

    private static final String PREFS_NAME = "MyPrefsFile";

    private TextView launchProfileTextView;
    private TextView deleteProfileTextView;

    private String profileId;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Inject
    AppDatabase appDatabase;

    public ProfileOptionsBottomSheet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_options_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        launchProfileTextView = view.findViewById(R.id.launchProfile);
        deleteProfileTextView = view.findViewById(R.id.deleteProfile);

        launchProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfile();
            }
        });

        deleteProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    private void launchProfile() {
        // Save profile ID to SharedPreferences using SharedPreferencesHelper
        SharedPreferencesHelper.saveString(requireContext(), "currentProfileId", profileId);

        // Navigate to HomeFragment or perform any other action
        Toast.makeText(getContext(), "Profile launched and saved!", Toast.LENGTH_SHORT).show();
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.homeFragment);
        dismiss();
    }

    private void deleteProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        db.collection("users").document(uid).collection("profiles").document(profileId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Delete from SQLite
                        appDatabase.userDao().deleteProfileById(profileId);

                        Toast.makeText(getContext(), "Profile deleted!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to delete profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showCustomDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_delete, null);

        // Create the AlertDialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        // Get references to the views in the custom layout
        TextView dismiss = dialogView.findViewById(R.id.dismiss);
        TextView delete = dialogView.findViewById(R.id.delete);

        // Set the click listener for the positive button
        dismiss.setOnClickListener(v -> {
            // Handle positive button click
            alertDialog.dismiss();
        });

        // Set the click listener for the negative button
        delete.setOnClickListener(v -> {
            // Handle negative button click

            deleteProfile();
            alertDialog.dismiss();
        });

        // Show the dialog
        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
