package com.my.dynamic_load_dex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    /*
        参考资料：
        https://blog.csdn.net/a2923790861/article/details/80539862
        https://www.jianshu.com/p/edb809d84d26
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void load_func(View view) {
        loadDexClass();
    }

    /**
     * 加载dex文件中的class
     */
    private void loadDexClass() {
        File cacheFile = getDir("dex", 0);
        String internalPath = cacheFile.getAbsolutePath() + File.separator + "out.jar";
        File desFile = new File(internalPath);
        try {
            if (!desFile.exists()) {
                if (!desFile.createNewFile()) return;
                FileUtils.copyFiles(this, "out.jar", desFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //下面开始加载dex class
        //1.待加载的dex文件路径，如果是外存路径，一定要加上读外存文件的权限,
        //2.解压后的dex存放位置，此位置一定要是可读写且仅该应用可读写
        //3.指向包含本地库(so)的文件夹路径，可以设为null
        //4.父级类加载器，一般可以通过Context.getClassLoader获取到，也可以通过ClassLoader.getSystemClassLoader()取到。
        DexClassLoader dexClassLoader = new DexClassLoader(internalPath, cacheFile.getAbsolutePath(), null, getClassLoader());
        try {
            //该name就是internalPath路径下的dex文件里面的ShowToastImpl这个类的包名+类名
            Class clz = dexClassLoader.loadClass("com.my.myshell_arrow.DexRes");
            Method dexRes = clz.getDeclaredMethod("getString");
            Toast.makeText(this, (CharSequence) dexRes.invoke(clz.newInstance()), Toast.LENGTH_LONG)
                    .show();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
