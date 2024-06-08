package com.pro.resume.craft.fragments.cvdata.coverletter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentAICoverBinding;


public class AICoverFragment extends Fragment {

    private FragmentAICoverBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAICoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}