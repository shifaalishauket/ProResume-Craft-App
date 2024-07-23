package com.pro.resume.craft.fragments.cvdata.skills;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentAddSkillsBinding;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.fragments.cvdata.languages.AddLanguagesFragment;
import com.pro.resume.craft.models.DTOSkills;
import com.pro.resume.craft.models.DTOlanguages;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddSkillsFragment extends Fragment {

  private FragmentAddSkillsBinding binding;

  @Inject
    AppDatabase appDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddSkillsFragment.this).popBackStack();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Language = Objects.requireNonNull(binding.skillEditText.getText()).toString();
                String selectedText = "";

                if (TextUtils.isEmpty(Language)){
                    binding.skillEditText.setError("required");
                    return;
                }

                int checkedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId();

                if (checkedRadioButtonId != -1) {
                    // A radio button is checked
                    RadioButton checkedRadioButton = binding.getRoot().findViewById(checkedRadioButtonId);

                    if (checkedRadioButton != null) {
                        selectedText = checkedRadioButton.getText().toString();
                    } else {
                        Log.e("AddLanguagesFragment", "RadioButton not found for ID: " + checkedRadioButtonId);
                    }
                } else {
                    Log.e("AddLanguagesFragment", "No RadioButton is selected");
                }

                DTOSkills dtOlanguages =  new DTOSkills(0,Language,selectedText,email);


                appDatabase.userDao().insertSkills(dtOlanguages);

                NavHostFragment.findNavController(AddSkillsFragment.this).popBackStack();

            }
        });
    }
}