package com.my.sharedpreferences1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);
    }
    /*
    创建SharedPreferences对象是读写数据的前提，这里需要说明两个参数：
        第一个参数： "sp_file"为文件名
        第二个参数： 文件的创建模式
    文件的创建模式有：（只需了解三种）
        Context.MODE_PRIVATE: 指定该SharedPreferences数据只能被本应用程序读、写。（默认模式）
        Context.MODE_WORLD_READABLE: 指定该SharedPreferences数据能被其他应用程序读，但不能写。
        Context.MODE_WORLD_WRITEABLE: 指定该SharedPreferences数据能被其他应用程序读，写。

    支持的数据类型有：boolean、float、int、long、string、Set
    */

    //写入数据
    public void write_func(View view) {
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件
        editor.putString("name", "Tom");
        editor.putInt("age", 28);
        editor.putBoolean("marrid", false);

        editor.putBoolean("boolean", true);
        editor.putFloat("float", 1.0f);
        editor.putInt("int", 1);
        editor.putLong("long", 1l);
        editor.putString("string", "1");
        Set<String> stringSet = new HashSet<>();
        stringSet.add("test1");
        stringSet.add("test2");
        editor.putStringSet("set", stringSet);
        //步骤4：提交
        editor.apply();
        Toast.makeText(this, "写入完成", Toast.LENGTH_LONG).show();
    }

    //读取数据
    public void read_func(View view) {
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("name", "");
        boolean b = sharedPreferences.getBoolean("boolean", false);
        float f = sharedPreferences.getFloat("float", 0);
        int i = sharedPreferences.getInt("int", 0);
        long l = sharedPreferences.getLong("long", 0);
        String s = sharedPreferences.getString("string", "");
        Set<String> set = sharedPreferences.getStringSet("set", new HashSet<String>());
        String result = userId + "\t" + b + "\t" + f + "\t" + i + "\t" + l + "\t" + s + "\t";
        //遍历
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            result += iterator.next() + "\t";
        }
        show_board.setText(result);
        Toast.makeText(this, "读取完成", Toast.LENGTH_LONG).show();
    }

    //删除指定数据
    public void delete_func(View view) {
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("name");
        editor.apply();
        Toast.makeText(this, "删除完成", Toast.LENGTH_LONG).show();
    }

    //清空数据
    public void clear_func(View view) {
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "清空完成", Toast.LENGTH_LONG).show();
    }

    //展示所有
    public void show_all_func(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        /*Map<String, ?>只能是只读模式，不能增加，因为增加的时候不知道该写入什么类型的值；Map<String, Object>可以读和写，只要是所有Object类的子类都可以。*/
        Map<String, ?> map = sharedPreferences.getAll();//将SharedPreferences 的data文件中的所有数据以map的形式取出
        String result = new String();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String tmp = "";
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Set) {
                String value1 = "";
                //遍历
                Set set = (Set) value;
                Iterator iterator = set.iterator();
                while (iterator.hasNext()) {
                    value1 += iterator.next() + ",";
                }
                tmp = key + ":" + value1 + "\n";
            } else {
                tmp = key + ":" + value + "\n";
            }
            result += tmp;
        }
        show_board.setText(result);
    }

}
