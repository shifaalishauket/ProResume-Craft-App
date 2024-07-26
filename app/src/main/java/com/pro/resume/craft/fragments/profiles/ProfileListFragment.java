package com.pro.resume.craft.fragments.profiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.models.DTOProfile;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileListFragment extends Fragment {

    private FragmentProfileListBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<DTOProfile> profileList;
    private ProfileAdapter profileAdapter;

    @Inject
    AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        profileList = new ArrayList<>();
        profileAdapter = new ProfileAdapter(requireContext(), profileList, profile -> {
            showBottomSheet(profile.getEmail());
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(profileAdapter);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.addProfileFragment);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
    }

    private void showBottomSheet(String profileId) {
        ProfileOptionsBottomSheet bottomSheet = new ProfileOptionsBottomSheet();
        bottomSheet.setProfileId(profileId); // Set profile ID to bottom sheet
        bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
    }

    private void fetchProfiles() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Handle the case where user is not authenticated
            return;
        }

        String uid = currentUser.getUid();
        db.collection("users").document(uid).collection("profiles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            profileList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                DTOProfile profile = document.toObject(DTOProfile.class);
                                DTOProfile profile1 = appDatabase.userDao().findByEmail(profile.getEmail());

                                if (profile1 == null){
                                    appDatabase.userDao().insert(profile);
                                }

                                if (profile != null) {
                                    profileList.add(profile);
                                }
                            }
                            if (profileList.isEmpty()){
                                binding.noDataText.setVisibility(View.VISIBLE);
                                binding.noDataView.setVisibility(View.VISIBLE);
                            }else{
                                binding.noDataText.setVisibility(View.GONE);
                                binding.noDataView.setVisibility(View.GONE);
                            }
                            profileAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchProfiles();
    }
}
