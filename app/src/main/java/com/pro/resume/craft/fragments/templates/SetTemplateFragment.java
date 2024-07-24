package com.pro.resume.craft.fragments.templates;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentSetTemplateBinding;
import com.pro.resume.craft.databinding.FragmentSplashBinding;
import com.pro.resume.craft.fragments.profiles.ProfileFragment;
import com.pro.resume.craft.models.DTOTemplate;
import com.pro.resume.craft.utils.DepthPageTransformer;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetTemplateFragment extends Fragment {

    private FragmentSetTemplateBinding binding;

    @Inject
    AppDatabase appDatabase;

    int[] imageResources = {
            R.drawable.temp_1,
            R.drawable.temp_2,
            R.drawable.temp_3,
            R.drawable.temp_4,
            R.drawable.temp_5,
            R.drawable.temp_6,
            R.drawable.temp_7,
            R.drawable.temp_8,
            R.drawable.temp_9,
            R.drawable.temp_10,
            R.drawable.temp_11
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSetTemplateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        TemplateAdapter adapter = new TemplateAdapter(imageResources);
        binding.viewPager.setAdapter(adapter);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(50));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.82f + r * 0.16f);
            }
        });

        // Set the PageTransformer
        binding.viewPager.setPageTransformer(transformer);

        new TabLayoutMediator(binding.intoTabLayout, binding.viewPager,
                (tab, position) -> {
                    // Configure tabs here if needed
                }).attach(); // The Magical Line


        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");


        binding.selectTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = binding.viewPager.getCurrentItem();
                int layoutResId = getLayoutResIdForPosition(currentItem);

                DTOTemplate datamodel = new DTOTemplate(0, layoutResId, email);

                DTOTemplate dtoTemplate = appDatabase.userDao().getTemplateByEmail(email);

                if (dtoTemplate == null){
                    appDatabase.userDao().insertTemplate(datamodel);
                }else{
                    dtoTemplate.setTempletename(layoutResId);
                    appDatabase.userDao().insertTemplate(dtoTemplate);
                }

                NavHostFragment.findNavController(SetTemplateFragment.this).navigate(R.id.previewResumeFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SetTemplateFragment.this).navigateUp();
            }
        });

        binding.viewPager.setCurrentItem(bundle.getInt("position",0));
    }

    private int getLayoutResIdForPosition(int position) {
        switch (position) {
            case 0: return R.layout.template_1;
            case 1: return R.layout.template_2;
            case 2: return R.layout.template_3;
            case 3: return R.layout.template_4;
            case 4: return R.layout.template_5;
            case 5: return R.layout.template_6;
            case 6: return R.layout.template_7;
            case 7: return R.layout.template_8;
            case 8: return R.layout.template_9;
            case 9: return R.layout.template_10;
            case 10: return R.layout.template_11;
            case 11: return R.layout.template_12;
            default: throw new IllegalArgumentException("Invalid position: " + position);
        }
    }
}