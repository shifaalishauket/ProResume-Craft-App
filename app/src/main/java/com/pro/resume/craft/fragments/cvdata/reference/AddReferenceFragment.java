package com.pro.resume.craft.fragments.cvdata.reference;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentAddReferenceBinding;
import com.pro.resume.craft.databinding.FragmentReferenceBinding;


public class AddReferenceFragment extends Fragment {

    private FragmentAddReferenceBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddReferenceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}