package epf.client.util.ssl;

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
