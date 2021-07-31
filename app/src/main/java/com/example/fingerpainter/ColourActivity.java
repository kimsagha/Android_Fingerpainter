package com.example.fingerpainter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

public class ColourActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 1;
    private String[] colours;
    private RadioGroup radioGroup;
    private int selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour);

        // get array of colors 'listColours' from the resource paintColours.xml and store it in an
        // array of strings
        Resources res = getResources();
        colours = res.getStringArray(R.array.listColours);

        // get the current color from the intent that started the activity and "check" it
        Bundle bundle = getIntent().getExtras();
        selectedButton = bundle.getInt("currentButton");
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(radioGroup.getChildAt(selectedButton).getId());
        // invoked as a new radio button is selected --> update the variable with the new color
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            checkedId = radioGroup.getCheckedRadioButtonId();
            selectedButton = (Math.abs(checkedId) % 10)-1;
        });
    }

    // go back to the main activity with new color or current one if no change was made
    public void onClickButton (View v) {
        Intent intent = new Intent(ColourActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("selectedButton", selectedButton);
        bundle.putString("selectedColor", colours[selectedButton]);
        intent.putExtras(bundle);
        setResult(MY_REQUEST_CODE, intent);
        finish();
    }
}