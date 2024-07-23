package com.pro.resume.craft.fragments.profiles;

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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pro.resume.craft.R;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

public class ProfileOptionsBottomSheet extends BottomSheetDialogFragment {

    private static final String PREFS_NAME = "MyPrefsFile";

    private TextView launchProfileTextView;
    private TextView deleteProfileTextView;

    private String profileId;

    public ProfileOptionsBottomSheet() {
        // Required empty public constructor
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
                deleteProfile();
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
        // Delete profile logic
        Toast.makeText(getContext(), "Profile deleted!", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
