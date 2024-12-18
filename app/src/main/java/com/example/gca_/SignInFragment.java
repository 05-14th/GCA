package com.example.gca_;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;

public class SignInFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText etEmail, etPass;

    public SignInFragment() {
        super(R.layout.signin);  // Layout for this fragment
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signUpButton = view.findViewById(R.id.button3);
        Button signInButton = view.findViewById(R.id.button);
        etEmail = view.findViewById(R.id.etEmailLogin);
        etPass = view.findViewById(R.id.etPassLogin);
        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(v -> navigateToFragment(new SignUpFragment()));

        signInButton.setOnClickListener(v -> signInUser());
    }

    private void signInUser() {
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        if (!isValidEmail(email)) {
            Toast.makeText(getContext(), "Di-wastong format ng email. Mangyaring i-type ang wastong email.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(getContext(), "Mangyaring i-type ang password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable swipe during login attempt
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            activity.setSwipeEnabled(false);  // Disable swipe during login
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            Toast.makeText(getContext(), "\n" +
                                    "Matagumpay ang pag-sign in!", Toast.LENGTH_SHORT).show();

                            // After successful login, enable swipe and go to FrontPage
                            if (getActivity() instanceof MainActivity) {
                                MainActivity activity = (MainActivity) getActivity();
                                activity.onLoginSuccessful();  // Enable swipe and go to FrontPage
                            }
                        } else {
                            Toast.makeText(getContext(), "Paki-verify ang iyong email bago mag-sign in.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }




    private void deleteUnverifiedUser(FirebaseUser user) {
        if (user != null) {
            user.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Hindi na-verify ang iyong email. Ang iyong account ay tinanggal.", Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                    etPass.setText("");
                } else {
                    Toast.makeText(getContext(), "Nabigong tanggalin ang account: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void navigateToFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
