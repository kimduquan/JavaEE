/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client;

import openup.client.ssl.DefaultHostnameVerifier;
import openup.client.ssl.DefaultTrustManager;
import java.security.GeneralSecurityException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author FOXCONN
 */
public class Client implements AutoCloseable {

    private WebTarget webTarget;
    private javax.ws.rs.client.Client client;
    
    public Client(String url) {
        client = ClientBuilder.newBuilder()
                .sslContext(getSSLContext())
                .hostnameVerifier(getHostnameVerifier())
                .build();
        webTarget = client.target(url);
    }

    private HostnameVerifier getHostnameVerifier() {
        return new DefaultHostnameVerifier();
    }

    private SSLContext getSSLContext() {
        TrustManager x509 = new DefaultTrustManager();
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("SSL");
            ctx.init(null, new TrustManager[]{x509}, null);
        } 
        catch (GeneralSecurityException ex) {
        }
        return ctx;
    }

    @Override
    public void close() throws Exception {
        client.close();
    }
    
    public WebTarget getWebTarget(){
        return webTarget;
    }
}
