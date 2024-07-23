package com.pro.resume.craft.fragments.cvdata.qualification;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentAddQualificationBinding;
import com.pro.resume.craft.databinding.FragmentReferenceBinding;
import com.pro.resume.craft.fragments.cvdata.experience.AddExperienceFragment;
import com.pro.resume.craft.models.DTOEducation;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddQualificationFragment extends Fragment {

  private FragmentAddQualificationBinding binding;

  @Inject
  AppDatabase appDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddQualificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");



        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/yyyy"; // In which you need to display
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                binding.startDateText.setText(sdf.format(myCalendar.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener enddate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/yyyy"; // In which you need to display
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                binding.endDateText.setText(sdf.format(myCalendar.getTime()));
            }
        };

        binding.startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(requireContext(), date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.present.addOnCheckedStateChangedListener(new MaterialCheckBox.OnCheckedStateChangedListener() {
            @Override
            public void onCheckedStateChangedListener(@NonNull MaterialCheckBox checkBox, int state) {
                if (checkBox.isChecked()){
                    binding.endDateInput.setVisibility(View.GONE);
                }else{
                    binding.endDateInput.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(requireContext(), enddate, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddQualificationFragment.this).popBackStack();
            }
        });



        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String degree = Objects.requireNonNull(binding.degreeEditText.getText()).toString();
                String institute = Objects.requireNonNull(binding.instituteText.getText()).toString();
                String grade = Objects.requireNonNull(binding.gradeText.getText()).toString();
                String startDate = Objects.requireNonNull(binding.startDateText.getText()).toString();
                String endDate = Objects.requireNonNull(binding.endDateText.getText()).toString();
                boolean tillDateChecked = binding.present.isChecked();

                boolean isValid = true;

                if (degree.isEmpty()) {
                    binding.degreeEditText.setError("Title cannot be empty");
                    isValid = false;
                }
                if (institute.isEmpty()) {
                    binding.instituteText.setError("Company cannot be empty");
                    isValid = false;
                }
                if (grade.isEmpty()) {
                    binding.gradeText.setError("Description cannot be empty");
                    isValid = false;
                }
                if (startDate.isEmpty()) {
                    binding.startDateText.setError("Start Date cannot be empty");
                    isValid = false;
                }

                if (!tillDateChecked && endDate.isEmpty()) {
                    binding.endDateText.setError("End Date cannot be empty");
                    isValid = false;
                }

                if (tillDateChecked) {
                    endDate = "Till Date";
                }

                if (isValid) {
                    DTOEducation dtoExperience = new DTOEducation(0,degree,startDate,endDate,"",grade,institute,email);
                    appDatabase.userDao().insertEducation(dtoExperience);
                    NavHostFragment.findNavController(AddQualificationFragment.this).popBackStack();
                }

            }
        });
    }
}