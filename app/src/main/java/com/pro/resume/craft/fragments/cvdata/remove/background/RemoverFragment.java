package com.pro.resume.craft.fragments.cvdata.remove.background;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.databinding.FragmentRemoverBinding;


public class RemoverFragment extends Fragment {

    private FragmentRemoverBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRemoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}