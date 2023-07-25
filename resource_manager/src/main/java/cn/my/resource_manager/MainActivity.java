package cn.my.resource_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "resource_manager";
    TextView show_board;

    /*
    目录	            资源类型
    anim/	        定义动画属性的XML文件。它们被保存在res/anim/文件夹下，通过R.anim类访问
    color/	        定义颜色状态列表的XML文件。它们被保存在res/color/文件夹下，通过R.color类访问
    drawable/	    图片文件，如.png,.jpg,.gif或者XML文件，被编译为位图、状态列表、形状、动画图片。它们被保存在res/drawable/文件夹下，通过R.drawable类访问
    layout/	        定义用户界面布局的XML文件。它们被保存在res/layout/文件夹下，通过R.layout类访问
    menu/	        定义应用程序菜单的XML文件，如选项菜单，上下文菜单，子菜单等。它们被保存在res/menu/文件夹下，通过R.menu类访问
    raw/	        任意的文件以它们的原始形式保存。需要根据名为R.raw.filename的资源ID，通过调用Resource.openRawResource()来打开raw文件
    values/	        包含简单值(如字符串，整数，颜色等)的XML文件。这里有一些文件夹下的资源命名规范。arrays.xml代表数组资源，通过R.array类访问；integers.xml代表整数资源，通过R.integer类访问；bools.xml代表布尔值资源，通过R.bool类访问；colors.xml代表颜色资源，通过R.color类访问；dimens.xml代表维度值，通过R.dimen类访问；strings.xml代表字符串资源，通过R.string类访问；styles.xml代表样式资源，通过R.style类访问
    xml/	        可以通过调用Resources.getXML()来在运行时读取任意的XML文件。可以在这里保存运行时使用的各种配置文件
    * */

    /*
    assets/         没有资源 ID，只能使用 AssetManager 读取这些文件。
    * */

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);
    }

    public void read_json(View view) {
        Context context = MainActivity.this;

        try {
            // 获取AssetManager
            AssetManager assetManager = context.getAssets();
            // 列出assets目录下所有文件
            String[] filePathList = assetManager.list("");
            assert filePathList != null;
            for (String filePath : filePathList) {
                Log.d(TAG, filePath);
            }
            Log.d(TAG, "----------------- assets目录下所有文件 -----------------");

            //通过管理器打开文件并读取
            InputStream inputStream = assetManager.open("config.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

            //转化为json对象
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            System.out.println(jsonObject.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}