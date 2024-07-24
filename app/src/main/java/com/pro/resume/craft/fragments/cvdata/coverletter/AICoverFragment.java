package com.pro.resume.craft.fragments.cvdata.coverletter;


import android.content.ClipData;
import android.content.ClipboardManager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pro.resume.craft.Api.OpenAiApi;
import com.pro.resume.craft.Api.OpenAiRequest;
import com.pro.resume.craft.Api.OpenAiResponse;
import com.pro.resume.craft.Api.RetrofitClient;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentAICoverBinding;
import com.pro.resume.craft.fragments.cvdata.experience.AddExperienceFragment;
import com.pro.resume.craft.models.DTOEducation;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.models.DTOPersonalInfo;
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
public class AICoverFragment extends Fragment {

    private FragmentAICoverBinding binding;

    @Inject
    AppDatabase appDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAICoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWriting();
            }
        });

        binding.retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWriting();
            }
        });

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToCopy = binding.outputTV.getText().toString();
                if (!textToCopy.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(requireActivity(), "Text copied to clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity(), "No text to copy", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.applyTmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("letter", binding.outputTV.getText().toString()); // Add your data to the bundle
                NavHostFragment.findNavController(AICoverFragment.this).navigate(R.id.coverLetterPreviewFragment,bundle);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AICoverFragment.this).popBackStack();
            }
        });
    }

    private void startWriting() {
        if (!NetworkUtils.isInternetAvailable(requireContext())) {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        DTOPersonalInfo dtoPersonalInfo = appDatabase.userDao().getPersonalInfo(email);
        ArrayList<DTOExperience> experiences = (ArrayList<DTOExperience>) appDatabase.userDao().getAllExperience(email);
        String coverLetterPrompt = generateCoverLetter(dtoPersonalInfo.getFirstName(), dtoPersonalInfo.getLastName(), dtoPersonalInfo.getProfession(), experiences);

        getResponseRetro(coverLetterPrompt);

    }

    public String generateCoverLetter(String firstName, String lastName, String profession, List<DTOExperience> experiences) {
        StringBuilder coverLetter = new StringBuilder();

        coverLetter.append("Hi, I'm providing you my details and you need to write a cover letter for that. Please make sure that it's effective. Please do not write the captions like here's the cover letter or something like that. just provide the cover letter. hope you'll understand. ");
        coverLetter.append("My name is ").append(firstName).append(" ").append(lastName).append(" and my profession is ").append(profession).append(". ");
        coverLetter.append("Here's my experience list:");

        for (DTOExperience experience : experiences) {
            coverLetter.append("\n")
                    .append("Company: ").append(experience.getCompany()).append(", ")
                    .append("Title: ").append(experience.getTitle()).append(", ")
                    .append("Start Date: ").append(experience.getStartDate()).append(", ")
                    .append("End Date: ").append(experience.getEndDate()).append(", ")
                    .append("Description: ").append(experience.getDescription()).append(".");
        }

        return coverLetter.toString();
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

                            binding.outputTV.setText(responseText);
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
}