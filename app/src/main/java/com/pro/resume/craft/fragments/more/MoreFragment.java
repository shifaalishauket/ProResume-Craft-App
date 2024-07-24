package com.pro.resume.craft.fragments.more;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentMoreBinding;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.fragments.cvdata.experience.AddExperienceFragment;

public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MoreFragment.this).navigate(R.id.profileListFragment);
            }
        });

        binding.logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                NavHostFragment.findNavController(MoreFragment.this).navigate(R.id.loginFragment);
            }
        });
    }
}