package com.myself.viewdemo2;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText mEdit,mEdit2;
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdit = (EditText)findViewById(R.id.edtId);
        mEdit2 = (EditText)findViewById(R.id.edtId2);
        mTv = (TextView)findViewById(R.id.idtId3);

        //点击键盘上的return回调方法
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String content = mEdit.getText().toString();
                Toast.makeText(MainActivity.this,"发起搜索:"+content, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //监听文本输入框
        mEdit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("xx","文字变成了:"+s);
                mTv.setText("还能输入:"+(30-s.length())+"个字");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
