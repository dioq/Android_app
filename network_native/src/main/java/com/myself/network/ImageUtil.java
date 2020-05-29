package com.myself.network;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static boolean havePermissions = false;//权限判断

    public static void checkAndGet_permission(final Activity activity) {
        try {
            //检测是否有写的权限
            int permission1 = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            //检测是否有读的权限
            int permission2 = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if ((permission1 != PackageManager.PERMISSION_GRANTED) || (permission2 != PackageManager.PERMISSION_GRANTED)) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 100);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10 * 1000);
                            checkAndGet_permission(activity);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            } else {
                havePermissions = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch = 0;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    /**
     * @param bs 把字节数组写到SDCard中,然后再读取该图片
     */
    private void writetoSDCard(byte[] bs, String imageName) {
        File dataDir = Environment.getExternalStorageDirectory();
        String path = dataDir.getPath() + "/DCIM/";
        try {
            FileOutputStream out = new FileOutputStream(new File(path + imageName)); //重新命名的图片为test.png.想要获取的图片的路径就是该图片的路径
            out.write(bs);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //根据assets里的一张图片,保存到sd卡然后获取路径(为了测试图片上传功能)
    public String getPathByImage(String imageName) {
        if (havePermissions == false) return null;
        InputStream abpath = getClass().getResourceAsStream("/assets/picture/" + imageName);
        try {
            writetoSDCard(InputStreamToByte(abpath), imageName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String path_img = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/" + imageName;
        return path_img;
    }

}
