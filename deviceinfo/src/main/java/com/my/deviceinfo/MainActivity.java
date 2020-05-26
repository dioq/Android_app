package com.my.deviceinfo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView show_board;
    final static String[] data = {
            "获取品牌", "获取型号", "获取手机分辨率", "获取运营商", "获取联网方式", "获取IMEI",
            "获取MEID", "获取操作系统", "获取wifi当前ip地址", "GPRS连接下的ip", "获取蓝牙地址",
            "获取序列号", "TestHook"
    };

    private DeviceUtil deviceInfoModel = DeviceUtil.getInstance();

    private void func_1(String param) {
        String result = deviceInfoModel.getPhoneBrand();
        show_board.setText(param + ":" + result);
    }

    private void func_2(String param) {
        String result = deviceInfoModel.getPhoneMODEL();
        show_board.setText(param + ":" + result);
    }

    private void func_3(String param) {
        String result = deviceInfoModel.getResolution(this);
        show_board.setText(param + ":" + result);
    }

    private void func_4(String param) {
        String result = deviceInfoModel.getNetOperator(this);
        show_board.setText(param + ":" + result);
    }

    private void func_5(String param) {
        String result = deviceInfoModel.getNetMode(this);
        show_board.setText(param + ":" + result);
    }

    private void func_6(String param) {
        String imei = deviceInfoModel.getIMEI(this);
        show_board.setText("imei:" + imei);
    }

    private void func_7(String param) {
        String result = deviceInfoModel.getMEID(this);
        show_board.setText("meid:" + result);
    }

    private void func_8(String param) {
        String result = deviceInfoModel.getOS();
        show_board.setText(param + " : " + result);
    }

    private void func_9(String param) {
        String result = deviceInfoModel.getLocalIpAddress(this);
        show_board.setText(param + ":" + result);
    }

    private void func_10(String param) {
        String result = deviceInfoModel.getLocalIpAddress();
        show_board.setText(param + ":" + result);
    }

    private void func_11(String param) {
        String result = deviceInfoModel.getBtAddressByReflection();
        show_board.setText(param + ":" + result);
    }

    private void func_12(String param) {
        String result = deviceInfoModel.getSerialNumber();
        show_board.setText(param + ":" + result);
    }

    private void func_13(String param) {
        String result = testFunc("teststring");
        show_board.setText(param + ":" + result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = (TextView) findViewById(R.id.show_board_id);

        ListView listView = (ListView) findViewById(R.id.lv_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, //context 上下文
                android.R.layout.simple_list_item_1, //行布局:系统自带的布局
                data//数据源
        ) {
            @Override
            public boolean isEnabled(int position) {
                return true;//允许点击
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ////重载该方法，在这个方法中，将每个Item的Gravity设置为CENTER
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        };
        listView.setAdapter(adapter);
        listView.addHeaderView(new ViewStub(this));//顶部分割线
        listView.addFooterView(new ViewStub(this));//底部分割线

        //设置listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String param = data[position - 1];
                switch (position) {
                    case 1:
                        func_1(param);
                        break;
                    case 2:
                        func_2(param);
                        break;
                    case 3:
                        func_3(param);
                        break;
                    case 4:
                        func_4(param);
                        break;
                    case 5:
                        func_5(param);
                        break;
                    case 6:
                        func_6(param);
                        break;
                    case 7:
                        func_7(param);
                        break;
                    case 8:
                        func_8(param);
                        break;
                    case 9:
                        func_9(param);
                        break;
                    case 10:
                        func_10(param);
                        break;
                    case 11:
                        func_11(param);
                        break;
                    case 12:
                        func_12(param);
                        break;
                    case 13:
                        func_13(param);
                        break;
                }
            }
        });
    }

    //测试方法
    public String testFunc(String str) {
        return str + " === end";
    }
}
