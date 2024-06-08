package com.pro.resume.craft.fragments.cvdata.skills;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.databinding.FragmentSkillsBinding;

public class SkillsFragment extends Fragment {

   private FragmentSkillsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}