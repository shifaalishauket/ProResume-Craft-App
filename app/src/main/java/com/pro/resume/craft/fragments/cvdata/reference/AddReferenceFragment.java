package com.pro.resume.craft.fragments.cvdata.reference;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentAddReferenceBinding;
import com.pro.resume.craft.databinding.FragmentReferenceBinding;
import com.pro.resume.craft.fragments.cvdata.experience.AddExperienceFragment;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.models.DTOReference;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddReferenceFragment extends Fragment {

    private FragmentAddReferenceBinding binding;

    @Inject
    AppDatabase appDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddReferenceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddReferenceFragment.this).popBackStack();
            }
        });



        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(binding.nameEditText.getText()).toString();
                String designation = Objects.requireNonNull(binding.designationText.getText()).toString();
                String organization = Objects.requireNonNull(binding.organizationText.getText()).toString();
                String phone = Objects.requireNonNull(binding.phoneText.getText()).toString();


                if (name.isEmpty()) {
                    binding.nameEditText.setError("Title cannot be empty");
                    return;
                }
                if (designation.isEmpty()) {
                    binding.designationText.setError("Company cannot be empty");
                    return;
                }
                if (organization.isEmpty()) {
                    binding.organizationText.setError("Description cannot be empty");
                    return;
                }

                DTOReference dtoReference = new DTOReference(0,name,designation,organization,phone,email);
                appDatabase.userDao().insertRefference(dtoReference);
                NavHostFragment.findNavController(AddReferenceFragment.this).popBackStack();

            }
        });
    }
}