/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 *
 * @author FOXCONN
 */
public class DefaultHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(final String hostname, final SSLSession sslSession) {
        return true;
    }
}
