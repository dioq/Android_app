package com.myself.network.SSL;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class SSLConfig {

    public static void set(SSLTrustWhich selectType) {
        try {
            SSLContext sslContext = CertificateManagers.getSSLContext(selectType);

            //将证书工厂 配置到 HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            //验证域名
            HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
        } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

}
