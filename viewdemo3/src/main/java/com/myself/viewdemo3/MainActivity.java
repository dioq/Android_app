package com.myself.viewdemo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonCh;
    private CheckBox checkBoxPlay, checkBoxEat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.countryId);
        radioButtonCh = (RadioButton)findViewById(R.id.radioChinaId);
        checkBoxPlay = (CheckBox) findViewById(R.id.playId);
        checkBoxEat = (CheckBox) findViewById(R.id.eat_foodId);

        //选中按钮变化时调用
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioChinaId:
                        Toast.makeText(MainActivity.this, "你选中了中国", Toast.LENGTH_SHORT).show();
                        checkBoxEat.setChecked(true);
                        break;
                    case R.id.radioAmericanId:
                        checkBoxEat.setChecked(false);
                        Toast.makeText(MainActivity.this, "美国被你选中了", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBoxPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MainActivity.this, "玩游戏" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        checkBoxEat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    radioButtonCh.setChecked(true);
                }
            }
        });
    }
}
