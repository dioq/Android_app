package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
//        textView.setText("a new World!");
        button = (Button) findViewById(R.id.button);

        ButtonListener buttonListener = new ButtonListener();
        button.setOnClickListener(buttonListener);
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            count++;
            textView.setText(count+"");
        }
    }

}
