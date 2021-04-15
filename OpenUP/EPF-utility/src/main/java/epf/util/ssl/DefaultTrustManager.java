/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author FOXCONN
 */
public class DefaultTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
    	toString();
    }

    @Override
    public void checkServerTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
    	toString();
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
