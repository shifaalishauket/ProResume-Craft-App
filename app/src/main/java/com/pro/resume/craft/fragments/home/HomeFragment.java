package com.pro.resume.craft.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentHomeBinding;
import com.pro.resume.craft.models.DTOSavedResumes;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
   private FragmentHomeBinding binding;

   @Inject
    AppDatabase appDatabase;

   ResumePreviewAdapter resumePreviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));

        resumePreviewAdapter = new ResumePreviewAdapter(new ResumePreviewAdapter.OnExperienceClickListener() {
            @Override
            public void onExperienceClick(DTOSavedResumes dtoSavedResumes) {
                showBottomSheet(dtoSavedResumes);
            }
        });
        binding.recyclerView.setAdapter(resumePreviewAdapter);
        appDatabase.userDao().getSavedResumes(email).observe(getViewLifecycleOwner(), new Observer<List<DTOSavedResumes>>() {
            @Override
            public void onChanged(List<DTOSavedResumes> experiences) {
                if (experiences.isEmpty()){
                    binding.noDataView.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else{
                    binding.noDataView.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.GONE); // Update the adapter with the new list
                }

                resumePreviewAdapter.submitList(experiences);
            }
        });


    }
    private void showBottomSheet(DTOSavedResumes cvData) {
        CVOptionsSheet bottomSheet = new CVOptionsSheet();
        bottomSheet.setCVData(cvData);
        bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
    }
}