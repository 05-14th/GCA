package com.example.gca_;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        // Set up the dropdown menu
        dropdownButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), dropdownButton);
            popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

            // Handle item selection
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.option1) {
                    // Load Fragment 1
                    navigateToFragment(new Option1());
                    return true;
                } else if (item.getItemId() == R.id.option2) {
                    // Load Fragment 2
                    navigateToFragment(new Option2());
                    return true;
                } else if (item.getItemId() == R.id.option3) {
                    // Load Fragment 3
                    navigateToFragment(new Option3());
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        return view;
    }

    // Navigate to a new fragment and remove FrontPage completely
    private void navigateToFragment(Fragment fragment) {
        if (getActivity() != null) {
            // Hide the ViewPager2
            ViewPager2 viewPager = getActivity().findViewById(R.id.viewpager);
            if (viewPager != null) {
                viewPager.setVisibility(View.GONE);
            }

            // Replace the current fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
