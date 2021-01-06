package com.myself.network.util;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch = 0;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte[] imgdata = bytestream.toByteArray();
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //根据assets里的一张图片,保存到sd卡然后获取路径(为了测试图片上传功能)
    public String getPathByImage(String imageName) {
        InputStream abpath = getClass().getResourceAsStream("/assets/picture/" + imageName);
        try {
            writetoSDCard(InputStreamToByte(abpath), imageName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/" + imageName;
    }

}
