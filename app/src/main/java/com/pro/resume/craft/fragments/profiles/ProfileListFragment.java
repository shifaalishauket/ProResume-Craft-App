package com.pro.resume.craft.fragments.profiles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.databinding.FragmentTemplatesBinding;


public class ProfileListFragment extends Fragment {

    private FragmentProfileListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}