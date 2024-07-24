package com.pro.resume.craft.fragments.cvdata.qualification;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentQualificationBinding;
import com.pro.resume.craft.databinding.FragmentReferenceBinding;
import com.pro.resume.craft.fragments.cvdata.experience.ExperienceAdapter;
import com.pro.resume.craft.fragments.cvdata.experience.ExperienceFragment;
import com.pro.resume.craft.fragments.cvdata.hobbies.HobbiesFragment;
import com.pro.resume.craft.models.DTOEducation;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QualificationFragment extends Fragment {

    private FragmentQualificationBinding binding;

    @Inject
    AppDatabase appDatabase;

    private EducationAdapter educationAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQualificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        educationAdapter = new EducationAdapter(new EducationAdapter.OnExperienceClickListener() {
            @Override
            public void onExperienceClick(DTOEducation experience) {
                showCustomDialog(experience.getId());
            }

        });

        binding.recyclerView.setAdapter(educationAdapter);

        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        appDatabase.userDao().getEducationByEmail(email).observe(getViewLifecycleOwner(), new Observer<List<DTOEducation>>() {
            @Override
            public void onChanged(List<DTOEducation> experiences) {
                if (experiences.isEmpty()){
                    binding.noDataView.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else{
                    binding.noDataView.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.GONE);
                }
                educationAdapter.submitList(experiences); // Update the adapter with the new list
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(QualificationFragment.this).navigate(R.id.addQualificationFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(QualificationFragment.this).popBackStack();
            }
        });
    }

    private void showCustomDialog(int id) {
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

            appDatabase.userDao().deleteEducationById(id);
            alertDialog.dismiss();
        });

        // Show the dialog
        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
}