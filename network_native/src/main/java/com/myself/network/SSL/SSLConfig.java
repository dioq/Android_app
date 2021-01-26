package com.myself.network.SSL;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLConfig {

    public static void set(SSLTrustWhich type, Activity activity) {
        SSLSocketFactory sslSocketFactory = null;
        switch (type) {
            case TrustAll:
                sslSocketFactory = getDefaultSSLSocketFactory();
                break;
            case JustTrustMe:
                sslSocketFactory = getSSLContextFactory(activity);
                break;
            case TrustMeTwoway:
                sslSocketFactory = getTwowaySSLContextFactory(activity);
                break;
        }

        if (sslSocketFactory == null) {
            System.out.println("证书生成出问题,请检查!");
            return;
        }

        //将证书工厂 配置到 HttpsURLConnection
        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
        //验证域名
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
    }

    // 不设置TrustManager[], 信任所有证书
    private static synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
        try {
            // 生成 TrustManagerFactory
            TrustManager[] trustManagers = new TrustManager[]{new MyTrustManager()};

            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    // 信任自己放在本地的证书(单向认验证)
    private static synchronized SSLSocketFactory getSSLContextFactory(Context context) {
        InputStream inputStream = null;
        try {
            // 生成 TrustManagerFactory
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            inputStream = context.getAssets().open("cert.pem");//这里导入SSL证书文件
            Certificate ca = certificateFactory.generateCertificate(inputStream);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(null, null);
            keystore.setCertificateEntry("ca", ca);
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keystore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private static final String KEY_STORE_TYPE_BKS = "bks";//证书类型 固定值
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型 固定值
    private static final String KEY_STORE_CLIENT_PATH = "client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_TRUST_PATH = "client.bks";//客户端验证服务器端的证书库
    private static final String KEY_STORE_PASSWORD = "changeit";// 客户端证书密码
    private static final String KEY_STORE_TRUST_PASSWORD = "changeit";//服务端证书库密码

    //信任自己放在本地的证书 (双向验证)
    private static synchronized SSLSocketFactory getTwowaySSLContextFactory(Context context) {
        InputStream ksIn = null;
        InputStream tsIn = null;
        try {
            // 服务器端需要验证的客户端证书
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
            ksIn = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
            keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            // 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
            tsIn = context.getResources().getAssets().open(KEY_STORE_TRUST_PATH);
            trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(trustStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException |
                CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ksIn != null) {
                    ksIn.close();
                }
                if (tsIn != null) {
                    tsIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
