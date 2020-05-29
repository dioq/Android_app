package com.myself.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class NetworkUtil {

    private static final String TAG = "t1";

    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static NetworkUtil instance;

    //构造器私有化
    private NetworkUtil() {
    }

    //方法同步，调用效率低
    public static synchronized NetworkUtil getInstance() {
        if (instance == null) {
            instance = new NetworkUtil();
        }
        return instance;
    }


    public void sendGet(String urlStr) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);//新建URL
            connection = (HttpURLConnection) url.openConnection();//发起网络请求
            connection.setRequestMethod("GET");//请求方式
            connection.setConnectTimeout(8000);//连接最大时间
            connection.setReadTimeout(8000);//读取最大时间
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));//写入reader
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /*
     * 传入一个Url地址  返回一个JSON字符串
     * 网络请求的情况分析:
     *   如果是404 500 ... 代表网络(Http协议)请求失败
     *   200 服务器返回成功
     *       业务成功  /业务失败
     * */
    public String doGet(String urlPath) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                return reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{ \"success\": false,\n   \"errorMsg\": \"后台服务器开小差了!\",\n     \"result\":{}}";
    }

    /*
     * 传入一个Url地址  返回一个JSON字符串
     * */
    public String doPost(String urlPath, HashMap<String, String> paramsMap) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //--------------------------------
            conn.setDoOutput(true);
            conn.getOutputStream().write(getParams(paramsMap).getBytes());
            //--------------------------------
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                return reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{ \"success\": false,\n   \"errorMsg\": \"后台服务器开小差了!\",\n     \"result\":{}}";
    }

    private static String getParams(HashMap<String, String> paramsMap) {
        String result = "";
        for (HashMap.Entry<String, String> entity : paramsMap.entrySet()) {
            result += "&" + entity.getKey() + "=" + entity.getValue();
        }
        return result.substring(1);
    }


    /**
     * @param filePath 要上传的文件绝对路径，如：e:/upload/SSD4k对齐分区.zip
     * @param urlStr   上传路径端口号和项目名称，如：http://192.168.1.209:9080/gjbmj
     */
    public void uploadFile(String filePath, String urlStr) {
        Log.e(TAG, "=========开始上传=============");
        String result = null;
        String uuid = UUID.randomUUID().toString();
        String BOUNDARY = uuid;
        String NewLine = "\r\n";

        try {
            File file = new File(filePath);
            Log.e(TAG, " file = " + file.length());
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setChunkedStreamingMode(1024 * 1024);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setConnectTimeout(50000);
            conn.setRequestProperty("User-Agent", "Android Client Agent");
            conn.setRequestProperty("Content-Type", "multipart/form-data; charset=utf-8; boundary=" + BOUNDARY);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setChunkedStreamingMode(1024 * 50);
            conn.connect();

            DataOutputStream bos = new DataOutputStream(conn.getOutputStream());
            DataInputStream bis = null;
            FileInputStream fis = null;
            if (file.exists()) {
                fis = new FileInputStream(file);
                byte[] buff = new byte[1024];
                bis = new DataInputStream(fis);
                int cnt = 0;
                //数据以--BOUNDARY开始
                bos.write(("--" + BOUNDARY).getBytes());
                //换行
                bos.write(NewLine.getBytes());
                //内容描述信息
                String content = "Content-Disposition: form-data; name=file; filename=\"" + file.getName() + "\"";
                bos.write(content.getBytes());
                bos.write(NewLine.getBytes());
                bos.write(NewLine.getBytes());
                //空一行后，开始通过流传输文件数据
                while ((cnt = bis.read(buff)) != -1) {
                    bos.write(buff, 0, cnt);
                }
                bos.write(NewLine.getBytes());
                //结束标志--BOUNDARY--
                bos.write(("--" + BOUNDARY + "--").getBytes());
                bos.write(NewLine.getBytes());
                bos.flush();
            }
            int res = conn.getResponseCode();
            Log.e(TAG, "res------------------>>" + res);
            if (res == 200) {
                InputStream input = conn.getInputStream();
                StringBuilder sbs = new StringBuilder();
                int ss;
                while ((ss = input.read()) != -1) {
                    sbs.append((char) ss);
                }
                result = sbs.toString();
                Log.e(TAG, "result------------------>>" + result);
            }
            bis.close();
            fis.close();
            bos.close();
            Log.e(TAG, "=========上传完成=============");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new File(filePath).delete();
        }
    }

}
