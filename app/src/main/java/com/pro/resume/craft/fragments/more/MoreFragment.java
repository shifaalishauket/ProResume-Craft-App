package com.pro.resume.craft.fragments.more;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentMoreBinding;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;

public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}