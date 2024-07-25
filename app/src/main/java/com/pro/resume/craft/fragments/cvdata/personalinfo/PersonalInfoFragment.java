package com.pro.resume.craft.fragments.cvdata.personalinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentPersonalInfoBinding;
import com.pro.resume.craft.models.DTOPersonalInfo;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonalInfoFragment extends Fragment {

    private FragmentPersonalInfoBinding binding;

    @Inject
    AppDatabase appDatabase;

    private DTOPersonalInfo personalInfo;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");
        DTOProfile profile = appDatabase.userDao().findByEmail(email);
        DTOPersonalInfo dtoPersonalInfo = appDatabase.userDao().getPersonalInfo(email);
        if (dtoPersonalInfo == null){
            if (profile != null){
                Glide.with(requireContext()).load(profile.getProfilePhotoUrl()).into(binding.profilePhoto);
                binding.emailText.setText(profile.getEmail());
                binding.firstNameText.setText(profile.getFirstName());
                binding.lastNameText.setText(profile.getLastName());
            }
        }else{
            Glide.with(requireContext()).load(dtoPersonalInfo.getProfilePhotoUrl()).into(binding.profilePhoto);
            binding.emailText.setText(dtoPersonalInfo.getEmail());
            binding.firstNameText.setText(dtoPersonalInfo.getFirstName());
            binding.lastNameText.setText(dtoPersonalInfo.getLastName());
            binding.phoneText.setText(dtoPersonalInfo.getPhonenumber());
            binding.professionText.setText(dtoPersonalInfo.getProfession());
            binding.addressText.setText(dtoPersonalInfo.getAddress());
        }


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = Objects.requireNonNull(binding.firstNameText.getText()).toString().trim();
                String lastName = Objects.requireNonNull(binding.lastNameText.getText()).toString().trim();
                String email = Objects.requireNonNull(binding.emailText.getText()).toString().trim();
                String profession = Objects.requireNonNull(binding.professionText.getText()).toString().trim();
                if (TextUtils.isEmpty(firstName)) {
                    binding.firstNameInput.setError("First Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    binding.lastNameInput.setError("Last Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    binding.emailInput.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(profession)){
                    binding.professionInput.setError("Profession is required");
                    return;
                }

                if (dtoPersonalInfo != null){

                    Log.d("TAG", "onClick:123 "+dtoPersonalInfo.getId());
                    appDatabase.userDao().updatePersonalInfoById(
                            dtoPersonalInfo.getId(),
                            binding.firstNameText.getText().toString(),
                            binding.lastNameText.getText().toString(),
                            dtoPersonalInfo.getProfilePhotoUrl(),
                            binding.emailText.getText().toString(),
                            dtoPersonalInfo.getUid(),
                            binding.professionText.getText().toString(),
                            binding.phoneText.getText().toString(),
                            binding.addressText.getText().toString()
                    );

                }else{
                    personalInfo = new DTOPersonalInfo(0,firstName,lastName,profile.getProfilePhotoUrl(),email,profile.getEmail(),profession,binding.phoneText.getText().toString(),binding.addressText.getText().toString());

                    appDatabase.userDao().insertPersonalInfo(personalInfo);

                }


                NavHostFragment.findNavController(PersonalInfoFragment.this).popBackStack();


            }
        });

        binding.removeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userData",personalInfo);
                NavHostFragment.findNavController(PersonalInfoFragment.this).navigate(R.id.removerFragment);
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PersonalInfoFragment.this).popBackStack();
            }
        });

    }
}