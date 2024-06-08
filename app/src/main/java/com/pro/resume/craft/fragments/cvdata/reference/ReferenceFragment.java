package com.pro.resume.craft.fragments.cvdata.reference;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.databinding.FragmentReferenceBinding;

public class ReferenceFragment extends Fragment {

    private FragmentReferenceBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReferenceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}