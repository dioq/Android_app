package com.example.checkbox2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private CheckBox eatBox;
    private CheckBox sleepBox;
    private CheckBox dotaBox;
    private CheckBox allCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eatBox = (CheckBox)findViewById(R.id.eatId);
        sleepBox = (CheckBox)findViewById(R.id.sleepId);
        dotaBox = (CheckBox)findViewById(R.id.dotaId);
        allCheckBox = (CheckBox)findViewById(R.id.allCheckbox);

        AllCheckListener listener = new AllCheckListener();
        allCheckBox.setOnCheckedChangeListener(listener);
    }

    class AllCheckListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                System.out.println("all check");
                eatBox.setChecked(true);
                sleepBox.setChecked(true);
                dotaBox.setChecked(true);
            }else {
                System.out.println("all unCheck");
                eatBox.setChecked(false);
                sleepBox.setChecked(false);
                dotaBox.setChecked(false);
            }
        }
    }
}
