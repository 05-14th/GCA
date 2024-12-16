package com.example.gca_;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {

    public SignInFragment() {
        super(R.layout.signin);  // The layout for this fragment
    }

    FirebaseAuth mAuth;
    private EditText etEmail, etPass;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signUpButton = view.findViewById(R.id.button3);
        Button signInButton = view.findViewById(R.id.button);
        etEmail = view.findViewById(R.id.etEmailLogin);
        etPass = view.findViewById(R.id.etPassLogin);
        mAuth =  FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new SignUpFragment());
            }
        });

        signInButton.setOnClickListener(v -> signInUser());
    }

    private void signInUser() {
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        // Validate email format
        if (!isValidEmail(email)) {
            Toast.makeText(getContext(), "Invalid email format. Please enter a valid email.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if password is valid
        if (password.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sign-in attempt
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Check if email is verified
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            // Proceed to the main activity or wherever the user should go
                            Toast.makeText(getContext(), "Sign-in successful!", Toast.LENGTH_SHORT).show();
                            navigateToFragment(new FrontPage());
                        } else {
                            // If the email is not verified, delete the user's credentials
                            if (user != null) {
                                deleteUnverifiedUser(user);
                            }
                        }
                    } else {
                        // Handle sign-in failure
                        Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteUnverifiedUser(FirebaseUser user) {
        if (user != null) {
            // Delete user credentials if the email is not verified
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Your email is not verified. Your account has been deleted.", Toast.LENGTH_SHORT).show();
                            etPass.setText("");
                            etEmail.setText("");
                        } else {
                            Toast.makeText(getContext(), "Failed to delete account: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Email format validation
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void navigateToFragment(Fragment fragment) {
        if (getActivity() != null) {
            // Replace the current fragment and add the transaction to the back stack
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null) // This ensures you can go back
                    .commit();
        }
    }
}