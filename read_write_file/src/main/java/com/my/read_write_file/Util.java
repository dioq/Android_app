package com.my.read_write_file;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Util {

    String loadFromSDFile(String fname) {
        String result = null;
        try {
            File dataDir = Environment.getExternalStorageDirectory();
            String path = dataDir.getPath() + "/" + fname;
            File f = new File(path);
            System.out.println(f.getAbsoluteFile());
            if (!f.exists()) {
                return null;
            }
            int length = (int) f.length();
            byte[] buff = new byte[length];
            FileInputStream fin = new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result = new String(buff, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    void writeToSDFile(String content, String fname) {
        try {
            File dataDir = Environment.getExternalStorageDirectory();
            String path = dataDir.getPath() + "/" + fname;
            File f = new File(path);
            System.out.println(f.getAbsoluteFile());
            if (!f.exists()) {
                boolean isOr = f.createNewFile();
                if (!isOr) {
                    return;
                }
            }
            byte[] data = content.getBytes();
            // 创建基于文件的输出流
            FileOutputStream fos = new FileOutputStream(f);
            // 把数据写入到输出流
            fos.write(data);
            // 关闭输出流
            fos.close();
            System.out.println("输入完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
