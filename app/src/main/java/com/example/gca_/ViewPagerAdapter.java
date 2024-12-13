package com.example.gca_;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FrontPage();
            case 1:
                return new SignInFragment();
            case 2:
                return new MaterialFragment(); // Add the new fragment here
            default:
                return new FrontPage();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Update the count to reflect the new fragment
    }
}
