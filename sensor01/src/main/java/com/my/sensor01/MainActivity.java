package com.my.sensor01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "001";

    private SensorManager sensorManager;
    private Vibrator vibrator;
    private Sensor accelerometerSensor;
    private SensorEventListener sensorEventListener;

    private String vector;
    private TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);

        initSetting();
        sensorShake();
    }

    /**
     * 准备工作，获取传感器、震动服务、音频播放
     */
    private void initSetting() {
        //获取传感器服务管理对象
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        //获取传感器对象
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册传感器监听器
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //震动服务对象
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * 传感器震动, 界面显示消息
     */
    private void sensorShake() {
        //创建监听器对象
        //作为一个标准值
        //200:摇晃了200毫秒之后，开始震动
        //500：震动持续的时间，震动持续了500毫秒。
        sensorEventListener = new SensorEventListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSensorChanged(SensorEvent event) {
                int type = event.sensor.getType();
                if (type == Sensor.TYPE_ACCELEROMETER) {
                    float[] values = event.values;
                    float x = values[0];
                    float y = values[1];
                    float z = values[2];
                    //作为一个标准值   最小低于 10 &最大值不超过 20
                    int coll = 16;

                    String valuesStr = "x : " + x + "\t\ty : " + y + "\t\tz : " + z + "\n";
                    Log.v(TAG,valuesStr);

                    vector += valuesStr;
                    showResponse(vector);

                    if (Math.abs(x) > coll | Math.abs(y) > coll | Math.abs(z) > coll) {
                        //200:摇晃了200毫秒之后，开始震动
                        //500：震动持续的时间，震动持续了500毫秒。
                        long[] pattern = {200, 2000};
                        vibrator.vibrate(pattern, -1);
                        Toast.makeText(MainActivity.this, "摇一摇拆红包", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    //  注册传感器监听器
    @Override
    protected void onStart() {
        super.onStart();
//        Log.v(TAG,"--------> onStart");
        if (sensorManager != null) {
            sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    //   注销传感器监听器
    @Override
    protected void onPause() {
        super.onPause();
//        Log.v(TAG,"--------> onPause");
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    // 在主线程显示视图控件
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show_board.setText(response);//设置TextView的内容
            }
        });
    }
}
