package com.pro.resume.craft.fragments.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.FragmentLoginBinding;
import com.pro.resume.craft.utils.Utils;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
                String password = Objects.requireNonNull(binding.passEditText.getText()).toString().trim();

                if (TextUtils.isEmpty(email)) {
                    binding.emailEditText.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    binding.passEditText.setError("Password is required.");
                    return;
                }



                loginUser(email, password);
            }
        });

        binding.forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.forgetPasswordFragment);
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.signUpFragment);
            }
        });

    }

    private void loginUser(String email, String password) {
        Utils.disableInteraction(false, requireActivity());
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Utils.disableInteraction(true, requireActivity());
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Authentication Successful.", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(requireView()).navigate(R.id.profileListFragment);

                            // Navigate to another fragment or activity
                        } else {
                            Utils.disableInteraction(true, requireActivity());
                            binding.progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
