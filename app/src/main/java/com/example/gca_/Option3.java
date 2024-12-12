package com.example.gca_;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
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

        // Make the draggable words respond to drag events
        TextView draggableWord1 = view.findViewById(R.id.draggableWord1);
        TextView draggableWord2 = view.findViewById(R.id.draggableWord2);
        TextView draggableWord3 = view.findViewById(R.id.draggableWord3);
        TextView draggableWord4 = view.findViewById(R.id.draggableWord4);

        // Add drag listeners to the draggable words
        View.OnTouchListener touchListener = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("word", ((TextView) v).getText());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(data, shadowBuilder, null, 0);
                return true;
            }
            return false;
        };

        draggableWord1.setOnTouchListener(touchListener);
        draggableWord2.setOnTouchListener(touchListener);
        draggableWord3.setOnTouchListener(touchListener);
        draggableWord4.setOnTouchListener(touchListener);

        // Add drag listeners to the drop targets
        FrameLayout dropTarget1 = view.findViewById(R.id.dropTarget1);
        FrameLayout dropTarget2 = view.findViewById(R.id.dropTarget2);
        FrameLayout dropTarget3 = view.findViewById(R.id.dropTarget3);
        FrameLayout dropTarget4 = view.findViewById(R.id.dropTarget4);

        View.OnDragListener dragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // In the drag started action, we don't need to do anything
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Highlight the drop target when the dragged view enters
                        v.setBackgroundColor(Color.GRAY);
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        // Reset the highlight when the dragged view exits
                        v.setBackgroundColor(Color.TRANSPARENT);
                        return true;

                    case DragEvent.ACTION_DROP:
                        // Handle the drop event
                        ClipData clipData = event.getClipData();
                        String droppedWord = clipData.getItemAt(0).getText().toString();

                        // Drop the word onto the target
                        if (v instanceof FrameLayout) {
                            TextView target = new TextView(getContext());
                            target.setText(droppedWord);
                            ((FrameLayout) v).removeAllViews();
                            ((FrameLayout) v).addView(target);
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        // Reset any UI changes after the drag operation ends
                        v.setBackgroundColor(Color.TRANSPARENT);
                        return true;

                    default:
                        break;
                }
                return false;
            }
        };

        dropTarget1.setOnDragListener(dragListener);
        dropTarget2.setOnDragListener(dragListener);
        dropTarget3.setOnDragListener(dragListener);
        dropTarget4.setOnDragListener(dragListener);
    }
}
