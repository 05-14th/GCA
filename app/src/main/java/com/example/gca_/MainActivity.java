package com.example.gca_;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainActivity extends FragmentActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Check if there are fragments in the back stack
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(); // Remove the current fragment from back stack
        } else {
            // Show the ViewPager2 if it was hidden
            if (viewPager != null && viewPager.getVisibility() == View.GONE) {
                viewPager.setVisibility(View.VISIBLE); // Show ViewPager2
            } else {
                super.onBackPressed(); // Exit the app if nothing else to go back to
            }
        }
    }

    /**
     * Navigates the ViewPager2 to the FrontPage fragment when the "Continue" button is pressed.
     */
    public void navigateToFrontPage() {
        if (viewPager != null) {
            viewPager.setCurrentItem(0, true); // Set the ViewPager2 to the first tab (FrontPage)
        }
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new FrontPage();  // FrontPage fragment
            } else {
                return new SignInFragment();  // SignInFragment fragment
            }
        }

        @Override
        public int getItemCount() {
            return 2;  // Number of fragments to swipe through
        }
    }
}
