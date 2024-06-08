package com.pro.resume.craft.fragments.templates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentSetTemplateBinding;
import com.pro.resume.craft.databinding.FragmentTemplatesBinding;


public class TemplatesFragment extends Fragment {

    private FragmentTemplatesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTemplatesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}