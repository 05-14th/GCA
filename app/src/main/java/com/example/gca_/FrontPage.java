package com.example.gca_;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class FrontPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.front_page, container, false);

        Button dropdownButton = view.findViewById(R.id.option_button);
        ImageButton infoButton = view.findViewById(R.id.imageButton);
        ImageButton manualButton = view.findViewById(R.id.imageButton2);

        // Set up the dropdown menu
        dropdownButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), dropdownButton);
            popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

            // Handle item selection
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.option1) {
                    // Load Option1 Fragment and add to back stack
                    navigateToFragment(new Option1());
                    return true;
                } else if (item.getItemId() == R.id.option2) {
                    // Load Option2 Fragment and add to back stack
                    navigateToFragment(new Option2());
                    return true;
                } else if (item.getItemId() == R.id.option3) {
                    // Load Option3 Fragment and add to back stack
                    navigateToFragment(new Option3());
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new InfoFragment());
            }
        });

        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new ManualFragment());
            }
        });

        // Navigate to InfoFragment when the info button is pressed
        //infoButton.setOnClickListener(v -> navigateToFragment(new InfoFragment()));

        return view;
    }

    // Navigate to a new fragment and add to back stack
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

