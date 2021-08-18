/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.security;

import epf.client.gateway.GatewayUtil;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.tests.client.ClientUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import epf.util.security.PasswordUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FOXCONN
 */
public class SecurityUtil {
    
	private static final Logger logger = Logging.getLogger(SecurityUtil.class.getName());
    
	public static String login(String username, String password) {
    	String token = null;
    	try(Client client = ClientUtil.newClient(GatewayUtil.get("security"))){
    		token = Security.login(client, username, PasswordUtil.hash(username, password.toCharArray()), GatewayUtil.get("tests").toURL());
    	}
    	catch(Exception ex) {
    		logger.log(Level.SEVERE, "login", ex);
    	}
    	return token;
    }
    
    public static void logOut(String token) {
    	try(Client client = ClientUtil.newClient(GatewayUtil.get("security"))){
    		client.authorization(token);
    		token = Security.logOut(client);
    	}
    	catch(Exception ex) {
    		logger.log(Level.SEVERE, "logOut", ex);
    	}
    }
    
    public static Token auth(String token) throws Exception {
    	try(Client client = ClientUtil.newClient(GatewayUtil.get("security"))){
    		client.authorization(token);
    		return Security.authenticate(client);
    	}
    }
    
    public static String revoke(String token) throws Exception {
    	try(Client client = ClientUtil.newClient(GatewayUtil.get("security"))){
    		client.authorization(token);
    		return Security.revoke(client);
    	}
    }
}
