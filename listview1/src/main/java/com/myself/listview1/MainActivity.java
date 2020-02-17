package com.myself.listview1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.获取ListView对象
        ListView listView = (ListView) findViewById(R.id.lv_main);
        //2.准备数据
//        String[] data = {"初识Android", "开发环境搭建", "基础控件I", "基础控件II", "线性布局", "相对布局","初识Android", "开发环境搭建", "基础控件I", "基础控件II", "线性布局", "相对布局","初识Android", "开发环境搭建", "基础控件I", "基础控件II", "线性布局", "相对布局"};
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("logo", R.drawable.logo_music);
            map.put("title", "千千静听 index:" + i);
            map.put("version", "版本: 8.4." + i);
            map.put("size", "大小:2" + i + "1M");
            list.add(map);
        }

        //3.准备适配器Adapter
   /*     ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, //context 上下文
                android.R.layout.simple_list_item_1, //行布局:系统自带的布局
                data//数据源
        );*/
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                list,
                R.layout.item,
                new String[]{"logo", "title", "version", "size"},
                new int[]{R.id.logoId, R.id.titleId, R.id.versionId, R.id.sizeId}
        );
        //4.将适配器关联到ListView
        listView.setAdapter(adapter);

        //设置listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = list.get(position);
                String title = map.get("title").toString();
                Toast.makeText(MainActivity.this,title,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
