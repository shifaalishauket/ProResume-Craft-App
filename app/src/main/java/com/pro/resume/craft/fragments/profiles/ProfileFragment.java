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

import com.pro.resume.craft.Api.OpenAiApi;
import com.pro.resume.craft.Api.OpenAiRequest;
import com.pro.resume.craft.Api.OpenAiResponse;
import com.pro.resume.craft.Api.RetrofitClient;
import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentProfileBinding;
import com.pro.resume.craft.models.DummyModelDetails;
import com.pro.resume.craft.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {

   private FragmentProfileBinding binding;
   private NavigationDetailsAdapter navigationDetailsAdapter;

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
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.AICoverFragment);
//                startWriting();
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

    private void startWriting() {
            if (!NetworkUtils.isInternetAvailable(requireContext())) {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            getResponseRetro("hello");

    }


    private void getResponseRetro(String userMessage) {
        if (NetworkUtils.isInternetAvailable(requireContext())) {
            OpenAiApi openAiApi = RetrofitClient.getRetrofitInstance(requireContext()).create(OpenAiApi.class);

            OpenAiRequest.Message systemMessage = new OpenAiRequest.Message("system", "You are a helpful assistant.");
            OpenAiRequest.Message userMsg = new OpenAiRequest.Message("user", userMessage);
            OpenAiRequest requestBody = new OpenAiRequest("gpt-3.5-turbo-0125", Arrays.asList(systemMessage, userMsg));

            Call<OpenAiResponse> call = openAiApi.getChatCompletion(
                    "Bearer sk-None-wc6Kz358qRKsibYbIT5cT3BlbkFJYsC4fj51vxN7T6CA8Gi9",
                    requestBody
            );

            call.enqueue(new Callback<OpenAiResponse>() {
                @Override
                public void onResponse(Call<OpenAiResponse> call, retrofit2.Response<OpenAiResponse> response) {
                    if (response.isSuccessful()) {
                        OpenAiResponse openAiResponse = response.body();
                        if (openAiResponse != null && !openAiResponse.getChoices().isEmpty()) {
                            String responseText = openAiResponse.getChoices().get(0).getMessage().getContent();
                            Log.e("TAG", "onResponse: " + responseText);
                        } else {
                            Log.e("OpenAiApi", "No response from API");
                        }
                    } else {
                        Log.e("OpenAiApi", "Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<OpenAiResponse> call, Throwable t) {
                    Log.e("OpenAiApi", "Error: " + t.getMessage());
                }
            });
        }
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