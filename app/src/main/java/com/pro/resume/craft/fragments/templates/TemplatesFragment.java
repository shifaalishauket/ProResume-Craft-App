package com.pro.resume.craft.fragments.templates;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentSetTemplateBinding;
import com.pro.resume.craft.databinding.FragmentTemplatesBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TemplatesFragment extends Fragment {

    private FragmentTemplatesBinding binding;

    private ImageGridAdapter adapter;


    int[] imageResources = {
            R.drawable.temp_1,
            R.drawable.temp_2,
            R.drawable.temp_3,
            R.drawable.temp_4,
            R.drawable.temp_5,
            R.drawable.temp_6,
            R.drawable.temp_7,
            R.drawable.temp_8,
            R.drawable.temp_9,
            R.drawable.temp_10,
            R.drawable.temp_11
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTemplatesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ImageGridAdapter(imageResources);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.setAdapter(adapter);
    }
}