package com.myself.network.SSL;

import android.app.Application;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/*        for (int i = 0; i < chain.length; i++) {
            X509Certificate x509Certificate = chain[0];
            PublicKey publicKey = x509Certificate.getPublicKey();
            // newPublicKey就是证书的公钥
            String newPublicKey = new BigInteger(1, publicKey.getEncoded()).toString(16);
            System.out.println("Server newPublicKey : \n" + newPublicKey);
        }*/
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
