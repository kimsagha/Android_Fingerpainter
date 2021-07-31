package com.example.fingerpainter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class BrushActivity extends AppCompatActivity {

    private static Bundle bundle;
    private static final int MY_REQUEST_CODE = 2;
    private int size;
    private int brushShape;
    private EditText sizeInput;
    private RadioGroup radioGroup;

    // create bundle as a singleton so size and shape are parsed to the same bundle
    public static Bundle getBundle() {
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_brush);

        // get the intent that started the activity to access the values chosen last time the
        // user started this activity (those values were sent back to the main activity for storing)
        Bundle iBundle = getIntent().getExtras();
        size = iBundle.getInt("currentSize");
        // display the current size in the activity for the user to see
        sizeInput = findViewById((R.id.editTextNumber));
        sizeInput.setText(String.valueOf(size));
        // "check" the button for the current shape
        brushShape = iBundle.getInt("currentShape");
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(radioGroup.getChildAt(brushShape).getId());
        // invoked as a radio button is selected and updated the value of the shape and puts it in
        // a bundle to be sent to the main activity
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            checkedId = radioGroup.getCheckedRadioButtonId();
            brushShape = (Math.abs(checkedId) % 10)-1;
            getBundle().putInt("shape", brushShape);
        });
    }

    // invoked as the user presses the button that takes it back to the main activity with the
    // drawing interface, takes result (size and shape) back with request code
    // if no changes have been made, it will take current values
    public void onClickButton (View view) {
        String input = sizeInput.getText().toString();
        if (input == Integer.toString(size)) {
        } else {
            size = Integer.parseInt(input);
        }
        getBundle().putInt("size", size);

        Intent intent = new Intent(BrushActivity.this, MainActivity.class);
        intent.putExtras(getBundle());
        setResult(MY_REQUEST_CODE, intent);
        finish();
    }
}