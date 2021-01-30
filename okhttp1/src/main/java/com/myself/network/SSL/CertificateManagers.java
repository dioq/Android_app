package com.myself.network.SSL;

import com.myself.network.utils.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class CertificateManagers {

    private static final String KEY_STORE_TYPE_BKS = "bks";//证书类型 固定值(Android 端用bks类型)
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型 固定值
    private static final String KEY_STORE_CLIENT_PATH = "client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_TRUST_PATH = "client.bks";//客户端验证服务器端的证书库
    private static final String KEY_STORE_PASSWORD = "changeit";// 客户端证书密码
    private static final String KEY_STORE_TRUST_PASSWORD = "changeit";//服务端证书库密码

    /*
     * KeyManagerFactory 保存自己的证书
     * TrustManagerFactory 保存其他人的证书, 如服务器的
     * */

    private static KeyManager[] getKeyManagers() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        // 服务器端需要验证的客户端证书
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
        InputStream ksIn = MyApplication.getContext().getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
        keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
        keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        ksIn.close();
        return keyManagers;
    }

    private static TrustManager[] getTrustManagers() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        // 客户端信任的服务器端证书
        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
        InputStream tsIn = MyApplication.getContext().getResources().getAssets().open(KEY_STORE_TRUST_PATH);
        trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
        trustManagerFactory.init(trustStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        tsIn.close();
        return trustManagers;
    }

    public static SSLContext getSSLContext(SSLTrustWhich selectType) throws KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException, UnrecoverableKeyException {
        KeyManager[] keyManagers = null;
        TrustManager[] trustManagers = null;
        switch (selectType) {
            case TrustAll:
                /*
                 * 只配置 trustManagers, 客户端只验证服务器是单向验证
                 * 这里信任所有的证书
                 * */
                trustManagers = new TrustManager[]{new MyTrustManager()};
                break;
            case TrustMeOneway:
                /*
                 * 只配置 trustManagers, 客户端只验证服务器证书是单向验证
                 * 公钥证书固定. 只信任自己指定的服务器证书
                 * */
                trustManagers = CertificateManagers.getTrustManagers();
                break;
            case TrustMeTwoway:
                // 配置 trustManagers,keyManagers,客户端验证服务器证书、服务器也验证客户端证书，是双向验证
                keyManagers = CertificateManagers.getKeyManagers();
                trustManagers = CertificateManagers.getTrustManagers();
                break;
        }

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, new SecureRandom());
        return sslContext;
    }

}
