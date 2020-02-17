package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private CheckBox eatBox;
    private CheckBox sleepBox;
    private CheckBox dotaBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eatBox = (CheckBox) findViewById(R.id.eatId);
        sleepBox = (CheckBox) findViewById(R.id.sleepId);
        dotaBox = (CheckBox) findViewById(R.id.dotaId);

        CheckBoxListener listener = new CheckBoxListener();
        eatBox.setOnCheckedChangeListener(listener);
        sleepBox.setOnCheckedChangeListener(listener);
        dotaBox.setOnCheckedChangeListener(listener);

        /*OnBoxClickListener listener = new OnBoxClickListener();
        eatBox.setOnClickListener(listener);
        sleepBox.setOnClickListener(listener);
        dotaBox.setOnClickListener(listener);*/
    }

    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.eatId) {
                System.out.println("eatBox");
                if (isChecked) {
                    System.out.println("Checked");
                }else {
                    System.out.println("unChecked");
                }
            }
        }
    }

    /*class OnBoxClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CheckBox box = (CheckBox) v;
            if(box.getId() == R.id.eatId) {
                System.out.println("eatBox");
                if (box.isChecked()){
                    System.out.println("eatBox is Checked");
                }else {
                    System.out.println("eatBox is unChecked");
                }
            }
            System.out.println("Checkout is clicked");
        }
    }*/
}
