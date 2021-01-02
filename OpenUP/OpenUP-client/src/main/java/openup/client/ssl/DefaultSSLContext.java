/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.ssl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import openup.client.ClientQueue;

/**
 *
 * @author FOXCONN
 */
public class DefaultSSLContext {
    
    public static SSLContext build(){
        try {
            TrustManager x509 = new DefaultTrustManager();
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, new TrustManager[]{x509}, null);
            return ctx;
        } 
        catch (Exception ex) {
            Logger.getLogger(ClientQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
