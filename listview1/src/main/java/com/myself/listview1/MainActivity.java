package com.myself.listview1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        ListView listView = findViewById(R.id.lv_main);
        //2.准备数据
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
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                list,//数据源
                R.layout.item,//cell模板
                new String[]{"logo", "title", "version", "size"},//上下两行: 模板 内容 和 vies'id 对应关系
                new int[]{R.id.logoId, R.id.titleId, R.id.versionId, R.id.sizeId}
        );
        //4.将适配器关联到ListView
        listView.setAdapter(adapter);

        //设置listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = list.get(position - 1);
                String title = map.get("title").toString();
                Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
