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
public class DefaultSSLContext {
	
	private static final Logger logger = Logger.getLogger(DefaultSSLContext.class.getName());
    
    public static SSLContext build(){
        try {
            TrustManager x509 = new DefaultTrustManager();
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, new TrustManager[]{x509}, null);
            return ctx;
        } 
        catch (Exception ex) {
        	logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
