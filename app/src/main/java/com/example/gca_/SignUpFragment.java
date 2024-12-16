package com.example.gca_;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;


    public SignUpFragment() {
        super(R.layout.signup);  // The layout for this fragment
    }

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        Button signInButton = view.findViewById(R.id.button4);
        Button signUpButton = view.findViewById(R.id.button2);
        EditText etFullname = view.findViewById(R.id.etFullname);
        EditText etEmail = view.findViewById(R.id.etEmailLogin);
        EditText etPassword = view.findViewById(R.id.etPassword);
        EditText etConfirmPass = view.findViewById(R.id.etConfirmPassword);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new SignInFragment());
            }
        });

        signUpButton.setOnClickListener(v -> {
            String fullName = etFullname.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPass.getText().toString().trim();

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Pakilagay ang hinihinging detalye", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.equals(confirmPassword)){
                signUpUser(fullName, email, password);
            }else{
                Toast.makeText(getActivity(), "Ang iyong password ay hindi nagtutugma", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void signUpUser(String fullName, String email, String password) {
        // Create user with email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // User created successfully
                        if (user != null) {
                            sendEmailVerification(user); // Send verification email
                        }
                    } else {
                        Toast.makeText(getContext(), "Bigong makapag sign up: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToDatabase(String userId, String fullName, String email, String password) {
        // Create a user object to save in Firebase
        User user = new User(fullName, email, password);

        // Save user data under their unique ID
        databaseReference.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Matagumpay na nakapag sign up", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Mayroong error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sendEmailVerification(FirebaseUser user) {
        // Send email verification
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Matagumpay na naipadala ang verification", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Bigong maipadala ang verification", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // User class for Realtime Database
    public static class User {
        public String fullName;
        public String email;
        public String password;

        public User() {
            // Default constructor required for Firebase
        }

        public User(String fullName, String email, String password) {
            this.fullName = fullName;
            this.email = email;
            this.password = password;
        }
    }

    private void showToast(String message) {
        if (isAdded()) {  // Check if fragment is still attached
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToFragment(Fragment fragment) {
        if (getActivity() != null) {
            // Replace the current fragment and add the transaction to the back stack
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .remove(this)
                    .commit();
        }
    }

}