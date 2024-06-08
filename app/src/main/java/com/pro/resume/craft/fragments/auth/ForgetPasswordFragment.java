package com.pro.resume.craft.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentForgetPasswordBinding;
import com.pro.resume.craft.databinding.FragmentLoginBinding;


public class ForgetPasswordFragment extends Fragment {

    private FragmentForgetPasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}