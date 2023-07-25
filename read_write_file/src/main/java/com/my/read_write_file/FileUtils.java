package com.my.read_write_file;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    //读取本地文本里的数据
    String readFromSDFile(String fname) {
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

            FileInputStream fin = new FileInputStream(f);
            int length = fin.available();
            byte[] buff = new byte[length];
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

    /*
     * 获取指定行数的数据
     * */
    public String readLineByIndex(String fname, int lineNumber) {
        // 如果要读取的行数,不在范围内就不执行读操作
        if (lineNumber < 0 || lineNumber > getTotalLines(fname)) {
            System.out.println("不在文件的行数范围之内。");
            return null;
        }

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null;
        try {
            File dataDir = Environment.getExternalStorageDirectory();
            String path = dataDir.getPath() + File.separator + fname;
            System.out.println("path : " + path);
            File f = new File(path);
            System.out.println(f.getAbsoluteFile());
            if (!f.exists()) {
                System.out.println("文件不存在");
                return null;
            }
            fis = new FileInputStream(f);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
            line = br.readLine();
            int num = 0;
            while (line != null) {
                if (lineNumber == ++num) {
//                    System.out.println("line " + lineNumber + ": " + line);
                    break;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    // 文件内容的总行数。
    public int getTotalLines(String fname) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        LineNumberReader reader = null;
        int lines = 0;
        try {
            File dataDir = Environment.getExternalStorageDirectory();
            String path = dataDir.getPath() + File.separator + fname;
            System.out.println("path : " + path);
            File f = new File(path);
            System.out.println(f.getAbsoluteFile());
            if (!f.exists()) {
                System.out.println("文件不存在");
                return 0;
            }

            fis = new FileInputStream(f);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
            reader = new LineNumberReader(br);
            String s = reader.readLine();
            while (s != null) {
                lines++;
                s = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }
}
