package com.pro.resume.craft.fragments.cvdata.skills;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentAddSkillsBinding;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;

public class AddSkillsFragment extends Fragment {

  private FragmentAddSkillsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}