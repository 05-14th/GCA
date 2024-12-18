package com.example.gca_;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private boolean isSignInFragmentVisible = true; // Track if SignInFragment is visible

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (!isSignInFragmentVisible) {
            // After login, only show FrontPage and MaterialFragment
            if (position == 0) {
                return new FrontPage(); // FrontPage is now the leftmost
            } else {
                return new MaterialFragment(); // MaterialFragment stays in the rightmost
            }
        } else {
            // Before login, all three fragments are available
            switch (position) {
                case 0:
                    return new SignInFragment();
                case 1:
                    return new FrontPage();
                case 2:
                    return new MaterialFragment();
                default:
                    return new FrontPage();
            }
        }
    }

    @Override
    public int getItemCount() {
        // Return 3 initially, but reduce it after login to 2
        return isSignInFragmentVisible ? 3 : 2;
    }

    // Method to update the fragment visibility after login
    public void updateFragments(boolean isVisible) {
        isSignInFragmentVisible = isVisible;
        notifyDataSetChanged(); // Update the ViewPagerAdapter
    }
}















