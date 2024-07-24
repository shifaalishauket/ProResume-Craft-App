package com.pro.resume.craft.fragments.cvdata.skills;

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
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.databinding.FragmentSkillsBinding;
import com.pro.resume.craft.fragments.cvdata.hobbies.HobbiesFragment;
import com.pro.resume.craft.fragments.cvdata.languages.LanguagesAdapter;
import com.pro.resume.craft.models.DTOSkills;
import com.pro.resume.craft.models.DTOlanguages;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SkillsFragment extends Fragment {

   private FragmentSkillsBinding binding;

   SkillsAdapter skillsAdapter;

   @Inject
    AppDatabase appDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        skillsAdapter = new SkillsAdapter(new SkillsAdapter.OnExperienceClickListener() {
            @Override
            public void onExperienceClick(DTOSkills experience) {
                showCustomDialog(experience.getId());
            }

        });

        binding.recyclerView.setAdapter(skillsAdapter);

        appDatabase.userDao().getSkillsByEmail(email).observe(getViewLifecycleOwner(), new Observer<List<DTOSkills>>() {
            @Override
            public void onChanged(List<DTOSkills> experiences) {
                if (experiences.isEmpty()){
                    binding.noDataView.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else{
                    binding.noDataView.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.GONE);
                    skillsAdapter.submitList(experiences); // Update the adapter with the new list
                }
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SkillsFragment.this).navigate(R.id.addSkillsFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SkillsFragment.this).popBackStack();
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

            appDatabase.userDao().deleteSkillsById(id);
            alertDialog.dismiss();
        });

        // Show the dialog
        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
}