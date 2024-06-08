package com.pro.resume.craft;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.pro.resume.craft.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setEvents();
    }

    private void setEvents() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        binding.bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id) {
                    case R.id.home:
                        navController.navigate(R.id.homeFragment);
                        break;
                    case R.id.templates:
                        navController.navigate(R.id.templatesFragment);
                        break;
                    case R.id.profile:
                        navController.navigate(R.id.profileFragment);
                        break;
                    case R.id.more:
                        navController.navigate(R.id.moreFragment);
                        break;
                }
            }
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            switch (id) {
                case R.id.homeFragment:
                    binding.bottomNav.setItemSelected(R.id.home, true);
                    binding.bottomNav.setVisibility(View.VISIBLE);
                    break;
                case R.id.templatesFragment:
                    binding.bottomNav.setItemSelected(R.id.templates, true);
                    binding.bottomNav.setVisibility(View.VISIBLE);
                    break;
                case R.id.profileFragment:
                    binding.bottomNav.setItemSelected(R.id.profile, true);
                    binding.bottomNav.setVisibility(View.VISIBLE);
                    break;
                case R.id.moreFragment:
                    binding.bottomNav.setItemSelected(R.id.more, true);
                    binding.bottomNav.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.bottomNav.setVisibility(View.GONE);
                    break;
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (navController.getCurrentDestination() != null) {
                    int currentDestinationId = navController.getCurrentDestination().getId();
                    if (currentDestinationId == R.id.homeFragment) {
                        finish();
                    } else {
                        navController.navigateUp();
                    }
                } else {
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}