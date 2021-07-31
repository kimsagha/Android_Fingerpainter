package com.example.fingerpainter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FingerPainterView v;
    private String color;
    private int colorButton = 5;
    private int brushSize = 20;
    private int brushShape = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // initialise activity or restore it using saved states
        setContentView(R.layout.activity_main); // set the content of the activity to be that of the xml file 'activity_main'
        v = findViewById((R.id.view)); // set the FingerPainterView to be 'view'
        v.load(getIntent().getData());

        if (savedInstanceState != null) { // if activity was interrupted before and has to be restored
            brushSize = savedInstanceState.getInt("size");
            v.setBrushWidth(brushSize);

            brushShape = savedInstanceState.getInt("currentShape");
            if (brushShape == 0) {
                v.setBrush(Paint.Cap.ROUND);
            } else {
                v.setBrush(Paint.Cap.SQUARE);
            }
            color = savedInstanceState.getString("selectedColor");
            v.setColour(Color.parseColor(color));
        }
    }

    // define what happens when other activities (which were started for result) give result back
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get result, and give it correct instructions based on its request code
            // request code = 1 --> result from color activity
            // request code = 2 --> result from brush activity
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            colorButton = data.getIntExtra("selectedButton", colorButton);
            color = data.getStringExtra("selectedColor");
            // set color to be that of the result (and update the value of the variable)
            v.setColour(Color.parseColor(color));
        }
        if(requestCode == 2) {
            brushSize = data.getIntExtra("size", brushSize);
            brushShape = data.getIntExtra("shape", brushShape);
            // set brush width to be that of the result (and update the value of the variable)
            v.setBrushWidth(brushSize);
            // set brush shape to be that of the result (and update the value of the variable)
            if (brushShape == 0) {
                v.setBrush(Paint.Cap.ROUND);
            } else {
                v.setBrush(Paint.Cap.SQUARE);
            }
        }
    }

    public void onClickButtonColour (View view) {
        // create an intent to start the color activity when the color button has been pressed
        Intent intent = new Intent(MainActivity.this, ColourActivity.class);
        Bundle bundle = new Bundle();
        // give the intent the updated value of the color chosen last so
        // the user knows what the current color is
        bundle.putInt("currentButton", colorButton);
        intent.putExtras(bundle);
        // start the activity and wait to get a result with request code 1 back
        startActivityForResult(intent, 1);
    }

    public void onClickButtonBrush (View view) {
        // create an intent to start the brush activity when the brush button has been pressed
        Intent intent = new Intent(MainActivity.this, BrushActivity.class);
        Bundle bundle = new Bundle();
        // give the intent the updated values of the size and shape chosen last so
        // the user knows what the current values are
        bundle.putInt("currentSize", brushSize);
        bundle.putInt("currentShape", brushShape);
        intent.putExtras(bundle);
        // start the activity and wait to get a result with request code 2 back
        startActivityForResult(intent, 2);
    }

    // invoked when the activity is temporarily destroyed
    // save important values needed to restore the activity
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("size", brushSize);
        outState.putInt("currentShape", brushShape);
        outState.putString("selectedColor", color);

    }

}