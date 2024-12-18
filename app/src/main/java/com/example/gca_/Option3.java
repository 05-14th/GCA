package com.example.gca_;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class Option3 extends Fragment {

    public Option3() {
        super(R.layout.fragment_option3);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the continue button
        Button continueButton = view.findViewById(R.id.continueButton);
        if (continueButton != null) {
            continueButton.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).onLoginSuccessful();
                }
            });
        }

        // Make the punctuation draggable
        TextView punctuation1 = view.findViewById(R.id.punctuation1); // comma (",")
        TextView punctuation2 = view.findViewById(R.id.punctuation2); // exclamation mark ("!")
        TextView punctuation3 = view.findViewById(R.id.punctuation3); // period (".")
        TextView punctuation4 = view.findViewById(R.id.punctuation4); // exclamation mark ("!")

        if (punctuation1 != null && punctuation2 != null && punctuation3 != null && punctuation4 != null) {
            // Set the touch listeners to make punctuation draggable
            View.OnTouchListener touchListener = (v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("punctuation", ((TextView) v).getText());
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, null, 0);
                    return true;
                }
                return false;
            };

            punctuation1.setOnTouchListener(touchListener);
            punctuation2.setOnTouchListener(touchListener);
            punctuation3.setOnTouchListener(touchListener);
            punctuation4.setOnTouchListener(touchListener);
        }

        // Initialize drop target words (textviews for the words with red font color)
        TextView dropTarget1 = view.findViewById(R.id.dropTarget1); // "magsasaing"
        TextView dropTarget2 = view.findViewById(R.id.dropTarget2); // "ako"
        TextView dropTarget3 = view.findViewById(R.id.dropTarget3); // "Saklolo"
        TextView dropTarget4 = view.findViewById(R.id.dropTarget4); // "nagpahinga"
        TextView dropTarget5 = view.findViewById(R.id.dropTarget5); // "Ang kanyang"
        TextView dropTarget6 = view.findViewById(R.id.dropTarget6); // "di matatawaran"

        // Debugging logs to check for null
        Log.d("Option3", "dropTarget5: " + dropTarget5);
        Log.d("Option3", "dropTarget6: " + dropTarget6);

        if (dropTarget1 != null && dropTarget2 != null && dropTarget3 != null && dropTarget4 != null
                && dropTarget5 != null && dropTarget6 != null) {
            // Set text for the drop targets with red words using Html.fromHtml() to parse the HTML
            dropTarget1.setText(Html.fromHtml("<font color=\"#FF0000\">magsasaing</font>"));
            dropTarget2.setText(Html.fromHtml("<font color=\"#FF0000\">ako</font>"));
            dropTarget3.setText(Html.fromHtml("<font color=\"#FF0000\">Saklolo</font>"));
            dropTarget4.setText(Html.fromHtml("<font color=\"#FF0000\">nagpahinga</font>"));
            dropTarget5.setText(Html.fromHtml("<font color=\"#FF0000\">Ang kanyang</font>"));
            dropTarget6.setText(Html.fromHtml("<font color=\"#FF0000\">di matatawaran</font>"));

            // Debugging logs to check if text is being set correctly
            Log.d("Option3", "dropTarget1 text: " + dropTarget1.getText());
            Log.d("Option3", "dropTarget2 text: " + dropTarget2.getText());
            Log.d("Option3", "dropTarget3 text: " + dropTarget3.getText());
            Log.d("Option3", "dropTarget4 text: " + dropTarget4.getText());
            Log.d("Option3", "dropTarget5 text: " + dropTarget5.getText());
            Log.d("Option3", "dropTarget6 text: " + dropTarget6.getText());

            // Set the drag listeners for the drop targets
            View.OnDragListener dragListener = (v, event) -> {
                int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Only change background if it's a red word target
                        if (((TextView) v).getCurrentTextColor() == Color.RED) {
                            v.setBackgroundColor(Color.GRAY);  // Highlight the target
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackgroundColor(Color.TRANSPARENT);  // Reset background
                        return true;

                    case DragEvent.ACTION_DROP:
                        ClipData clipData = event.getClipData();
                        String droppedPunctuation = clipData.getItemAt(0).getText().toString();
                        TextView targetWordView = (TextView) v;

                        // Only process drop if the word is red
                        if (targetWordView != null && targetWordView.getCurrentTextColor() == Color.RED) {
                            if (isCorrectDrop(targetWordView.getText().toString(), droppedPunctuation)) {
                                targetWordView.setText(targetWordView.getText().toString() + droppedPunctuation);
                                v.setBackgroundColor(Color.GREEN);  // Correct drop feedback
                                targetWordView.setTextColor(Color.BLACK);  // Word color changes after correct drop
                            } else {
                                v.setBackgroundColor(Color.RED);  // Incorrect drop feedback
                            }
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundColor(Color.TRANSPARENT);  // Reset background
                        return true;

                    default:
                        break;
                }
                return false;
            };

            dropTarget1.setOnDragListener(dragListener);
            dropTarget2.setOnDragListener(dragListener);
            dropTarget3.setOnDragListener(dragListener);
            dropTarget4.setOnDragListener(dragListener);
            dropTarget5.setOnDragListener(dragListener);
            dropTarget6.setOnDragListener(dragListener);
        }
    }

    private boolean isCorrectDrop(String word, String punctuation) {
        switch (word) {
            case "magsasaing":
                return punctuation.equals(",");
            case "ako":
                return punctuation.equals("!");
            case "Saklolo":
                return punctuation.equals("!");
            case "nagpahinga":
                return punctuation.equals(".");
            case "Ang kanyang":
                return punctuation.equals(".");
            case "di matatawaran":
                return punctuation.equals("!");
            default:
                return false;
        }
    }
}





