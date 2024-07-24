package com.pro.resume.craft.fragments.cvdata.coverletter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentCoverListBinding;
import com.pro.resume.craft.fragments.home.CVOptionsSheet;
import com.pro.resume.craft.fragments.home.HomeFragment;
import com.pro.resume.craft.fragments.home.ResumePreviewAdapter;
import com.pro.resume.craft.fragments.profiles.ProfileFragment;
import com.pro.resume.craft.models.DTOCoverLetter;
import com.pro.resume.craft.models.DTOSavedResumes;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CoverListFragment extends Fragment {

    private FragmentCoverListBinding binding;

    @Inject
    AppDatabase appDatabase;

    CoverPreviewAdapter coverPreviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCoverListBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));

        coverPreviewAdapter = new CoverPreviewAdapter(new CoverPreviewAdapter.OnCoverClickListener() {
            @Override
            public void onCoverClick(DTOCoverLetter coverLetter) {
                showBottomSheet(coverLetter);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CoverListFragment.this).navigate(R.id.AICoverFragment);
            }
        });

        binding.recyclerView.setAdapter(coverPreviewAdapter);
        appDatabase.userDao().findCoverByEmail(email).observe(getViewLifecycleOwner(), new Observer<List<DTOCoverLetter>>() {
            @Override
            public void onChanged(List<DTOCoverLetter> experiences) {
                if (experiences.isEmpty()){
                    binding.noDataView.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else{
                    binding.noDataView.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.GONE); // Update the adapter with the new list
                }

                coverPreviewAdapter.submitList(experiences);
            }
        });


    }
    private void showBottomSheet(DTOCoverLetter cvData) {
        CoverOptionsSheet bottomSheet = new CoverOptionsSheet();
        bottomSheet.setCoverData(cvData);
        bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
    }
}