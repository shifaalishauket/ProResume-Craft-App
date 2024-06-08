package com.pro.resume.craft.fragments.cvdata.objective;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;
import com.pro.resume.craft.databinding.FragmentPersonalInfoBinding;

public class ObjectiveFragment extends Fragment {

   private FragmentObjectiveBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentObjectiveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}