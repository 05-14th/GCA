package com.example.gca_;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private boolean isLoggedIn = false; // Track login state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Set default page based on login status
        if (isLoggedIn) {
            viewPager.setCurrentItem(1, false); // FrontPage for logged-in users
        } else {
            viewPager.setCurrentItem(0, false); // SignInFragment for non-logged-in users
        }

        // Lock navigation logic initially (prevent swipe before login)
        setSwipeEnabled(false);

        // Lock navigation to prevent access to SignInFragment after login
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Prevent access to SignInFragment after login
                if (isLoggedIn && position == 0) {
                    viewPager.setCurrentItem(1, false); // Redirect to FrontPage
                }
            }
        });
    }

    // Method to enable swiping after login
    public void onLoginSuccessful() {
        isLoggedIn = true; // Update login status

        // Remove SignInFragment and only show FrontPage and MaterialFragment
        adapter.updateFragments(true); // Pass true to keep only FrontPage and MaterialFragment

        // Navigate to FrontPage
        viewPager.setCurrentItem(1, true);

        // Enable navigation between FrontPage and MaterialFragment only
        setSwipeEnabled(true);
    }

    // Method to disable swiping (e.g., during login)
    public void setSwipeEnabled(boolean enabled) {
        viewPager.setUserInputEnabled(enabled);
    }
}



























