/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.util.ssl;

import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 *
 * @author FOXCONN
 */
public final class SSLContextHelper {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(SSLContextHelper.class.getName());
	
	private SSLContextHelper() {
		
	}
    
    /**
     * @return
     */
    public static SSLContext build(){
    	SSLContext ctx = null;
        try {
        	final TrustManager x509 = new DefaultTrustManager();
        	ctx = SSLContext.getInstance("SSL");
            ctx.init(null, new TrustManager[]{x509}, null);
        } 
        catch (Exception ex) {
        	LOGGER.throwing(SSLContextHelper.class.getName(), "build", ex);
        }
        return ctx;
    }
}
