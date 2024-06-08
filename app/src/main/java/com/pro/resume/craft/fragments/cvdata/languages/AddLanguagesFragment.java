package com.pro.resume.craft.fragments.cvdata.languages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentAddLanguagesBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;

public class AddLanguagesFragment extends Fragment {

    private FragmentAddLanguagesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddLanguagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}