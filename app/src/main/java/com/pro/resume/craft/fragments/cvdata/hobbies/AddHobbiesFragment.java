package com.pro.resume.craft.fragments.cvdata.hobbies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentAddHobbiesBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;
import com.pro.resume.craft.models.DTOHobbies;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AddHobbiesFragment extends Fragment {

    private FragmentAddHobbiesBinding binding;

    @Inject
    AppDatabase appDatabase;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddHobbiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddHobbiesFragment.this).popBackStack();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hobby = Objects.requireNonNull(binding.titleEditText.getText()).toString();

                if (TextUtils.isEmpty(hobby)){
                    binding.titleEditText.setError("required");
                    return;
                }
                DTOHobbies dtoHobbies = new DTOHobbies(0,hobby,email);
                appDatabase.userDao().inserthobby(dtoHobbies);

                NavHostFragment.findNavController(AddHobbiesFragment.this).popBackStack();

            }
        });

    }
}