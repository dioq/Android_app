package com.myself.network;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class NetworkSSLUtils {

    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static NetworkSSLUtils instance;

    //构造器私有化
    private NetworkSSLUtils() {
    }

    //方法同步，调用效率低
    public static synchronized NetworkSSLUtils getInstance() {
        if (instance == null) {
            instance = new NetworkSSLUtils();
        }
        return instance;
    }

    //服务器绑定的域名
    private static String host = "www.anant.club";

    /**
     * 获取Https的证书
     *
     * @param context Activity（fragment）的上下文
     * @return SSL的上下文对象
     */
    private static SSLContext getSSLContext(Context context) {
        SSLContext s_sSLContext = null;
        CertificateFactory certificateFactory = null;
        InputStream inputStream = null;
        KeyStore keystore = null;
        String tmfAlgorithm = null;
        TrustManagerFactory trustManagerFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");

            inputStream = context.getAssets().open("cert.pem");//这里导入SSL证书文件

            Certificate ca = certificateFactory.generateCertificate(inputStream);

            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(null, null);
            keystore.setCertificateEntry("ca", ca);

            tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keystore);

            // Create an SSLContext that uses our TrustManager
            s_sSLContext = SSLContext.getInstance("TLS");
            s_sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return s_sSLContext;
        } catch (CertificateException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET 请求
     *
     * @param urlStr 请求接口
     */
    public String doGet(String urlStr, Activity activity) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = new URL(urlStr);

            //SSLContext 初始化
            SSLContext sslContext = getSSLContext(activity);
            assert sslContext != null;
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslsession) {
                    if (host.equals(hostname)) {//判断域名是否和证书域名相等
                        return true;
                    } else {
                        return false;
                    }
                }
            });


            if (url.getProtocol().toLowerCase().equals("https")) {//判断是http还是https
                //https.setHostnameVerifier(DO_NOT_VERIFY);
                connection = https;
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }
            connection.setConnectTimeout(8000);//连接最大时间
            connection.setReadTimeout(8000);//读取最大时间
            connection.setRequestMethod("GET");
            //处理返回信息
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream(); //获取网络输入流 in
                reader = new BufferedReader(new InputStreamReader(in)); //转换成BufferedReader
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();
            } else {
                result = "http is failed.  " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * POST           restful接口        application/json
     *
     * @param urlStr 请求接口
     * @param param  参数
     */
    public String doPost(String urlStr, String param, Activity activity) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = new URL(urlStr);

            //SSLContext 初始化
            SSLContext sslContext = getSSLContext(activity);
            assert sslContext != null;
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslsession) {
                    if (host.equals(hostname)) {//判断域名是否和证书域名相等
                        return true;
                    } else {
                        return false;
                    }
                }
            });


            if (url.getProtocol().toLowerCase().equals("https")) {//判断是http还是https
                //https.setHostnameVerifier(DO_NOT_VERIFY);
                connection = https;
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }
            connection.setConnectTimeout(8000);//连接最大时间
            connection.setReadTimeout(8000);//读取最大时间
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestMethod("POST");
            //--------------------------------
            connection.setDoOutput(true);//是否写入参数
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(param.getBytes());//把参数的二进制格式 直接写入到网络输出流outputStream中
            //--------------------------------
            //处理返回信息
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();//获取网络输入流 in
                reader = new BufferedReader(new InputStreamReader(in)); //转换成BufferedReader
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();
            } else {
                result = "http is failed.  " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

}
