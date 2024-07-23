package com.pro.resume.craft.fragments.cvdata.experience;

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
import com.pro.resume.craft.databinding.FragmentAddExperienceBinding;
import com.pro.resume.craft.databinding.FragmentObjectiveBinding;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddExperienceFragment extends Fragment {

    private FragmentAddExperienceBinding binding;

    @Inject
    AppDatabase appDatabase;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddExperienceBinding.inflate(inflater, container, false);
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
                NavHostFragment.findNavController(AddExperienceFragment.this).popBackStack();
            }
        });



        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = Objects.requireNonNull(binding.titleEditText.getText()).toString();
                String commpany = Objects.requireNonNull(binding.companyText.getText()).toString();
                String description = Objects.requireNonNull(binding.descriptionText.getText()).toString();
                String startDate = Objects.requireNonNull(binding.startDateText.getText()).toString();
                String endDate = Objects.requireNonNull(binding.endDateText.getText()).toString();
                boolean tillDateChecked = binding.present.isChecked();

                boolean isValid = true;

                if (title.isEmpty()) {
                    binding.titleEditText.setError("Title cannot be empty");
                    isValid = false;
                }
                if (commpany.isEmpty()) {
                    binding.companyText.setError("Company cannot be empty");
                    isValid = false;
                }
                if (description.isEmpty()) {
                    binding.descriptionText.setError("Description cannot be empty");
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
                    endDate = "Till Date"; // Set end date to "Till Date" if checkbox is checked
                }

                if (isValid) {
                    DTOExperience dtoExperience = new DTOExperience(0,commpany,title,startDate,endDate,description,email);
                  appDatabase.userDao().insertExperiecne(dtoExperience);

                    NavHostFragment.findNavController(AddExperienceFragment.this).popBackStack();

                }

            }
        });
    }
}