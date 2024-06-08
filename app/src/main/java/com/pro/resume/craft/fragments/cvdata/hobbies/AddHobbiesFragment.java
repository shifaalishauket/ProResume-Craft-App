package com.pro.resume.craft.fragments.cvdata.hobbies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentAddHobbiesBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;


public class AddHobbiesFragment extends Fragment {

    private FragmentAddHobbiesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddHobbiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}