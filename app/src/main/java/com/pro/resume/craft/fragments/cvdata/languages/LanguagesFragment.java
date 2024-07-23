package com.pro.resume.craft.fragments.cvdata.languages;

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
import com.pro.resume.craft.databinding.FragmentLanguagesBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;
import com.pro.resume.craft.fragments.cvdata.hobbies.HobbiesAdapter;
import com.pro.resume.craft.fragments.cvdata.hobbies.HobbiesFragment;
import com.pro.resume.craft.models.DTOHobbies;
import com.pro.resume.craft.models.DTOlanguages;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LanguagesFragment extends Fragment {

    private FragmentLanguagesBinding binding;

    @Inject
    AppDatabase appDatabase;

    LanguagesAdapter languagesAdapter;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLanguagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        languagesAdapter = new LanguagesAdapter(new LanguagesAdapter.OnExperienceClickListener() {
            @Override
            public void onExperienceClick(DTOlanguages experience) {

            }

        });

        binding.recyclerView.setAdapter(languagesAdapter);

        appDatabase.userDao().getLanguagesByEmail(email).observe(getViewLifecycleOwner(), new Observer<List<DTOlanguages>>() {
            @Override
            public void onChanged(List<DTOlanguages> experiences) {
                if (experiences.isEmpty()){
                    binding.noDataView.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else{
                    binding.noDataView.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.GONE);
                    languagesAdapter.submitList(experiences); // Update the adapter with the new list
                }
            }
        });
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LanguagesFragment.this).navigate(R.id.addLanguagesFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LanguagesFragment.this).popBackStack();
            }
        });
    }
}