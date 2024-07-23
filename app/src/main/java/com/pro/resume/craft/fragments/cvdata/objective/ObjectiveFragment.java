package com.pro.resume.craft.fragments.cvdata.objective;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;
import com.pro.resume.craft.fragments.cvdata.personalinfo.PersonalInfoFragment;
import com.pro.resume.craft.models.DTOObjective;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ObjectiveFragment extends Fragment {

    @Inject
    AppDatabase appDatabase;

   private FragmentObjectiveBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentObjectiveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");
        DTOObjective objective = appDatabase.userDao().getObjectiveByEmail(email);

        if (objective != null){
            binding.descriptionText.setText(objective.getObjective());
        }

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String objectiveTxt = Objects.requireNonNull(binding.descriptionText.getText()).toString();
                if (TextUtils.isEmpty(objectiveTxt)){
                    binding.descriptionText.setError("Required");
                    return;
                }

                if (objective == null){

                    DTOObjective dtoObjective = new DTOObjective(0,objectiveTxt,email);
                    appDatabase.userDao().insertObjective(dtoObjective);
                }else{
                    objective.setObjective(objectiveTxt);
                    appDatabase.userDao().insertObjective(objective);
                }

                NavHostFragment.findNavController(ObjectiveFragment.this).popBackStack();
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ObjectiveFragment.this).popBackStack();
            }
        });

    }
}