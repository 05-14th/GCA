package com.example.gca_;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {
    public InfoFragment() {
        super(R.layout.fragment_info);  // The layout for this fragment
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton pkr = view.findViewById(R.id.imageButton3);
        ImageButton jsa = view.findViewById(R.id.imageButton7);
        ImageButton ins = view.findViewById(R.id.imageButton8);

        TextView name = view.findViewById(R.id.nameText);
        TextView title = view.findViewById(R.id.person_title);

        pkr.setOnClickListener(v -> {
            name.setText("Princess Key R. Reniva".toUpperCase());
            title.setText("Mananaliksik");

        });

        jsa.setOnClickListener(v -> {
            name.setText("Joanabell C. San Agustin".toUpperCase());
            title.setText("Mananaliksik");

        });

        ins.setOnClickListener(v -> {
            name.setText("Edwin R. Ichiano".toUpperCase() + ", PhD");
            title.setText("Tagapayo");

        });
    }
}