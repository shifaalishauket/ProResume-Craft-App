package com.pro.resume.craft.fragments.cvdata.hobbies;

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
import com.pro.resume.craft.databinding.FragmentHobbiesBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;
import com.pro.resume.craft.fragments.cvdata.experience.ExperienceAdapter;
import com.pro.resume.craft.fragments.cvdata.experience.ExperienceFragment;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.models.DTOHobbies;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HobbiesFragment extends Fragment {

    @Inject
    AppDatabase appDatabase;

    private FragmentHobbiesBinding binding;

    HobbiesAdapter hobbiesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHobbiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        hobbiesAdapter = new HobbiesAdapter(new HobbiesAdapter.OnExperienceClickListener() {
            @Override
            public void onExperienceClick(DTOHobbies experience) {
                showCustomDialog(experience.getId());
            }

        });

        binding.recyclerView.setAdapter(hobbiesAdapter);

        appDatabase.userDao().getHobbiesByEmail(email).observe(getViewLifecycleOwner(), new Observer<List<DTOHobbies>>() {
            @Override
            public void onChanged(List<DTOHobbies> experiences) {
                if (experiences.isEmpty()){
                    binding.noDataView.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else{
                    binding.noDataView.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.GONE);
                }
                hobbiesAdapter.submitList(experiences);
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HobbiesFragment.this).navigate(R.id.addHobbiesFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HobbiesFragment.this).popBackStack();
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

            appDatabase.userDao().deleteHobbiesById(id);
            alertDialog.dismiss();
        });

        // Show the dialog
        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


}