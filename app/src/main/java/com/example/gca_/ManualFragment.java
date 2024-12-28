package com.example.gca_;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ManualFragment extends Fragment {
    public ManualFragment() {
        super(R.layout.fragment_manual);  // The layout for this fragment
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button intindi = view.findViewById(R.id.agreeButton);
        intindi.setOnClickListener(v -> {
            navigateToFragment(new FrontPage());
        });
    }

    private void navigateToFragment(Fragment fragment) {
        if (getActivity() != null) {
            // Replace the current fragment and add the transaction to the back stack
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .remove(this) // This ensures you can go back
                    .commit();
        }
    }
}