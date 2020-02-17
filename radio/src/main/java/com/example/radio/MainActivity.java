package com.example.radio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rdioGroup;
    private RadioButton femaleButton;
    private RadioButton maleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdioGroup = (RadioGroup)findViewById(R.id.radioGroupId);
        femaleButton = (RadioButton)findViewById(R.id.femaleButtonId);
        maleButton = (RadioButton)findViewById(R.id.maleButtonId);

        RadioGroupListener listener = new RadioGroupListener();
        rdioGroup.setOnCheckedChangeListener(listener);

        RadioButtonListener radioButtonListener = new RadioButtonListener();
        femaleButton.setOnCheckedChangeListener(radioButtonListener);
    }

    class RadioButtonListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            System.out.println("isChecked -->" + isChecked);
        }
    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == femaleButton.getId()){
                System.out.println("选中了female");
            }else if (checkedId == maleButton.getId()) {
                System.out.println("选中male");
            }
        }
    }
}
