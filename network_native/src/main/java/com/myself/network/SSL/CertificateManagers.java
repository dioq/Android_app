package com.myself.network.SSL;

import com.myself.network.util.MyApplication;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

class CertificateManagers {

    private static final String CLIENT_CERT = "client.p12";//客户端证书
    private static final String CLIENT_CERT_PASSWORD = "zxcvbnm,.";// 客户端证书密码
    private static final String SERVER_CERT = "server.bks";//服务端证书
    private static final String SERVER_CERT_PASSWORD = "zxcvbnm,.";//服务端证书库密码

    /*
     * KeyManagerFactory 保存自己的证书
     * TrustManagerFactory 保存其他人的证书, 如服务器的
     * */

    private static KeyManager[] getKeyManagers() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        // 服务器端需要验证的客户端证书
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream ksIn = MyApplication.getContext().getResources().getAssets().open(CLIENT_CERT);
        keyStore.load(ksIn, CLIENT_CERT_PASSWORD.toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
        keyManagerFactory.init(keyStore, CLIENT_CERT_PASSWORD.toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        ksIn.close();
        return keyManagers;
    }

    private static TrustManager[] getTrustManagers() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        // 服务端证书
        KeyStore trustStore = KeyStore.getInstance("bks");
        InputStream tsIn =  MyApplication.getContext().getResources().getAssets().open(SERVER_CERT);
        trustStore.load(tsIn, SERVER_CERT_PASSWORD.toCharArray());
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
        trustManagerFactory.init(trustStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        tsIn.close();
        return trustManagers;
    }

    static SSLContext getSSLContext(SSLTrustWhich selectType) throws KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException, UnrecoverableKeyException {
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
