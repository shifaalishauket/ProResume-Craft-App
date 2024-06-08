package com.pro.resume.craft.fragments.cvdata.hobbies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentHobbiesBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;


public class HobbiesFragment extends Fragment {

    private FragmentHobbiesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHobbiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}