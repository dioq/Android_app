package com.my.listview2;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.show_board_id);

        //1.获取ListView对象
        ListView listView = findViewById(R.id.lv_main);
        //2.准备数据
        final String[] data = {"hook MD5", "hook DES", "hook AES", "hook PBE", "hook RSA"};
        //3.准备适配器Adapter
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
                //重载该方法，在这个方法中，将每个Item的Gravity设置为CENTER
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        };
        //4.将适配器关联到ListView
        listView.setAdapter(adapter);
        /*
         * 顶部和底部添加分割线,美观
         * */
        listView.addHeaderView(new ViewStub(this));//顶部分割线
        listView.addFooterView(new ViewStub(this));//底部分割线


        //设置listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position : " + position + " id : " + id);
                String str = data[position - 1];
                show_board.setText(str);
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
