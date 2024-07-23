package com.pro.resume.craft.fragments.cvdata.hobbies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentHobbiesBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;
import com.pro.resume.craft.fragments.cvdata.experience.ExperienceAdapter;
import com.pro.resume.craft.fragments.cvdata.experience.ExperienceFragment;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.models.DTOHobbies;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HobbiesFragment extends Fragment {

    @Inject
    AppDatabase appDatabase;

    private FragmentHobbiesBinding binding;

    HobbiesAdapter hobbiesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHobbiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        hobbiesAdapter = new HobbiesAdapter(new HobbiesAdapter.OnExperienceClickListener() {
            @Override
            public void onExperienceClick(DTOHobbies experience) {

            }

        });

        binding.recyclerView.setAdapter(hobbiesAdapter);

        appDatabase.userDao().getHobbiesByEmail(email).observe(getViewLifecycleOwner(), new Observer<List<DTOHobbies>>() {
            @Override
            public void onChanged(List<DTOHobbies> experiences) {
                if (experiences.isEmpty()){
                    binding.noDataView.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else{
                    binding.noDataView.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.GONE);
                    hobbiesAdapter.submitList(experiences); // Update the adapter with the new list
                }
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HobbiesFragment.this).navigate(R.id.addHobbiesFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HobbiesFragment.this).popBackStack();
            }
        });
    }


}