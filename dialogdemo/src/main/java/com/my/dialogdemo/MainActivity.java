package com.my.dialogdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //用xml写个自定义view,view上放多个控件
    public void showButtonDialogFragment(View view2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("请输入用户名和密码");
        builder.setMessage("有神秘礼品相送");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);

        final EditText username = (EditText) view.findViewById(R.id.username);
        final EditText password = (EditText) view.findViewById(R.id.password);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String a = username.getText().toString().trim();
                String b = password.getText().toString().trim();
                //    将输入的用户名和密码打印出来
                Toast.makeText(MainActivity.this, "用户名: " + a + ", 密码: " + b, Toast.LENGTH_SHORT).show();
                if (a.equals("dio") && b.equals("1234")) {
                    Util.joinQQGroup("BloW8W5O0LGfD0l8-WIE45rXwx7OB9qK", MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //无法多次添加,会覆盖
    public void showButtonDialogFragment2(View view2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("请输入用户名和密码");

        final EditText username = new EditText(this);
        final EditText password = new EditText(this);

        builder.setView(username);//这个会被覆盖
        builder.setView(password);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String a = username.getText().toString().trim();
                String b = password.getText().toString().trim();
                //    将输入的用户名和密码打印出来
                Toast.makeText(MainActivity.this, "用户名: " + a + ", 密码: " + b, Toast.LENGTH_SHORT).show();
                if (a.equals("dio") && b.equals("1234")) {
                    Util.joinQQGroup("BloW8W5O0LGfD0l8-WIE45rXwx7OB9qK", MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //用代码 自定义view，并添加到dialog
    public void showButtonDialogFragment3(View view2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入用户名和密码");
        final EditText editText1 = new EditText(this);
        editText1.setHint("请输入用户名");
        final EditText editText2 = new EditText(this);
        editText2.setHint("请输入密码");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editText1);
        layout.addView(editText2);
        builder.setView(layout);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(editText1.getText()) && TextUtils.isEmpty(editText2.getText())) {
                    Toast.makeText(MainActivity.this, "不能出现空值", Toast.LENGTH_SHORT).show();
                } else {
                    String a = editText1.getText().toString().trim();
                    String b = editText2.getText().toString().trim();
                    //    将输入的用户名和密码打印出来
                    Toast.makeText(MainActivity.this, "用户名: " + a + ", 密码: " + b, Toast.LENGTH_SHORT).show();
                    if (a.equals("dio") && b.equals("1234")) {
                        Util.joinQQGroup("BloW8W5O0LGfD0l8-WIE45rXwx7OB9qK", MainActivity.this);
                    } else {
                        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}
