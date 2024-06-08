package com.pro.resume.craft.fragments.profiles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentAddProfileBinding;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;


public class AddProfileFragment extends Fragment {

    private FragmentAddProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}