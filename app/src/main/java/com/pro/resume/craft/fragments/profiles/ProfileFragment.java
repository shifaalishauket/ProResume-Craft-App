package com.pro.resume.craft.fragments.profiles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.Api.OpenAiApi;
import com.pro.resume.craft.Api.OpenAiRequest;
import com.pro.resume.craft.Api.OpenAiResponse;
import com.pro.resume.craft.Api.RetrofitClient;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentProfileBinding;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.models.DummyModelDetails;
import com.pro.resume.craft.utils.NetworkUtils;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

   private FragmentProfileBinding binding;
   private NavigationDetailsAdapter navigationDetailsAdapter;
    @Inject
    AppDatabase appDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<DummyModelDetails> dummyModelList = getDummyModelList();
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");
        DTOProfile profile1 = appDatabase.userDao().findByEmail(email);
        binding.userName.setText(profile1.getFirstName()+" "+ profile1.getLastName());
        binding.profession.setText(profile1.getEmail());
        Glide.with(requireActivity())
                .load(profile1.getProfilePhotoUrl())
                .centerCrop()
                .placeholder(R.drawable.icon_profile_fill) // Optional placeholder image while loading
                .into(binding.profile);

        navigationDetailsAdapter = new NavigationDetailsAdapter(dummyModelList, new NavigationDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DummyModelDetails dummyModel) {
                if (dummyModel.getName().equals("Personal Details")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.personalInfoFragment);
                }else if (dummyModel.getName().equals("Objective")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.objectiveFragment);

                }else if (dummyModel.getName().equals("Experience")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.experienceFragment);

                }else if (dummyModel.getName().equals("Qualification")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.qualificationFragment);

                }else if (dummyModel.getName().equals("Languages")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.languagesFragment);

                }else if (dummyModel.getName().equals("Skills")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.skillsFragment);

                }else if (dummyModel.getName().equals("Hobbies")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.hobbiesFragment);

                }else if (dummyModel.getName().equals("References")){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.referenceFragment);
                }
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(navigationDetailsAdapter);

        binding.aiCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.coverListFragment);
            }
        });

        binding.selectTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.setTemplateFragment);
            }
        });

        binding.viewResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.previewResumeFragment);
            }
        });

    }

    private List<DummyModelDetails> getDummyModelList() {
        List<DummyModelDetails> list = new ArrayList<>();
        list.add(new DummyModelDetails(R.drawable.icon_personal, "Personal Details"));
        list.add(new DummyModelDetails(R.drawable.icon_objective, "Objective"));
        list.add(new DummyModelDetails(R.drawable.icon_experience, "Experience"));
        list.add(new DummyModelDetails(R.drawable.icon_qualification, "Qualification"));
        list.add(new DummyModelDetails(R.drawable.icon_language, "Languages"));
        list.add(new DummyModelDetails(R.drawable.icon_skills, "Skills"));
        list.add(new DummyModelDetails(R.drawable.ic_hobbies, "Hobbies"));
        list.add(new DummyModelDetails(R.drawable.icon_references, "References"));
        return list;
    }


}