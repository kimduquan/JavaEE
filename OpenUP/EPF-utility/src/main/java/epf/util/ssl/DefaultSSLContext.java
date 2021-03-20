/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.ssl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 *
 * @author FOXCONN
 */
public final class DefaultSSLContext {
	
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(DefaultSSLContext.class.getName());
	
	private DefaultSSLContext() {
		
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
        	logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return ctx;
    }
}
