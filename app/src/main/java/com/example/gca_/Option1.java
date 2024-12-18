package com.example.gca_;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Option1 extends Fragment {
    public Option1() {
        super(R.layout.fragment_option1);  // The layout for this fragment
    }
    private static final Set<String> FILIPINO_DICTIONARY = new HashSet<>();
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            loadDictionaryFromCSV();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error loading dictionary: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        EditText inputEditText = view.findViewById(R.id.wawastuhan);
        Button correctButton = view.findViewById(R.id.iwasto);
        LinearLayout btnLayout = view.findViewById(R.id.btn_group);
        ImageButton checkBtn = view.findViewById(R.id.checkBtn);
        ImageButton exBtn = view.findViewById(R.id.x_btn);

        btnLayout.setVisibility(View.GONE);

        correctButton.setOnClickListener(v -> {
            String inputSentence = inputEditText.getText().toString();
            if (inputSentence.isEmpty()) {
                Toast.makeText(getContext(), "Maglagay muna ng salita", Toast.LENGTH_SHORT).show();
            } else {
                String correctedSentence = correctSentence(inputSentence);
                inputEditText.setText(correctedSentence);
                btnLayout.setVisibility(View.VISIBLE);
                correctButton.setVisibility(View.GONE);
            }
        });

        checkBtn.setOnClickListener(v -> {
            btnLayout.setVisibility(View.GONE);
            correctButton.setVisibility(View.VISIBLE);
        });

        exBtn.setOnClickListener(v -> {
            inputEditText.setText("");
            btnLayout.setVisibility(View.GONE);
            correctButton.setVisibility(View.VISIBLE);
        });
    }

    private void loadDictionaryFromCSV() throws Exception {
        InputStream is = getResources().openRawResource(R.raw.tagalog_dataset);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length > 0) {
                FILIPINO_DICTIONARY.add(values[1].toLowerCase().trim());
            }
        }

        reader.close();
        is.close();
    }

    // Method to correct the entire sentence
    private String correctSentence(String sentence) {
        String[] words = sentence.split("\\s+");
        StringBuilder corrected = new StringBuilder();

        for (String word : words) {
            String cleanedWord = word.replaceAll("[^a-zA-Z-ñÑ]", "").toLowerCase(); // Normalize
            String correctedWord = FILIPINO_DICTIONARY.contains(cleanedWord)
                    ? word // Keep the original word if valid
                    : getClosestWord(cleanedWord);
            corrected.append(correctedWord != null ? correctedWord : word).append(" ");
        }

        return corrected.toString().trim();
    }

    // Find the closest word from the dictionary
    private String getClosestWord(String input) {
        String closestWord = null;
        int minDistance = Integer.MAX_VALUE;

        for (String word : FILIPINO_DICTIONARY) {
            int distance = levenshteinDistance(input, word);
            if (distance < minDistance) {
                minDistance = distance;
                closestWord = word;
            }
        }

        return minDistance <= 2 ? closestWord : null; // Suggest only if distance is small
    }

    // Levenshtein Distance Algorithm
    private int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}
