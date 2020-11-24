package com.my.read_write_file;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    //读取本地文本里的数据
    String loadFromSDFile(String fname) {
        String result = null;
        try {
            File dataDir = Environment.getExternalStorageDirectory();
            String path = dataDir.getPath() + File.separator + fname;
            System.out.println("path : " + path);
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

    //把string 写到本地文件里(会覆盖)
    void write_to_localFile(String content, String fname) {
        try {
            File dataDir = Environment.getExternalStorageDirectory();
            String path = dataDir.getPath() + File.separator + fname;
            System.out.println("path : " + path);
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

    //把string 写到本地文件里(往尾部追加数据)
    public void write_toEnd_localFile(String content, String fname) {
        try {
            File dataDir = Environment.getExternalStorageDirectory();
            String path = dataDir.getPath() + File.separator + fname;
            System.out.println("path : " + path);
            File f = new File(path);
            System.out.println("path : " + f.getAbsolutePath());
            if (!f.exists()) {
                f.createNewFile();
            }
            System.out.println(f.getAbsoluteFile());
            BufferedWriter out = new BufferedWriter(new FileWriter(f, true));
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
