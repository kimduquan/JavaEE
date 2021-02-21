/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import epf.client.config.ConfigNames;
import epf.client.security.Security;
import epf.util.client.Client;
import epf.util.security.PasswordHash;
import java.net.URI;
import java.net.URL;

/**
 *
 * @author FOXCONN
 */
public class SecurityUtil {
    
    private static URL gatewayUrl;
    
    public static String login(String unit, String username, String password) throws Exception {
    	if(gatewayUrl == null) {
    		gatewayUrl = new URL(System.getProperty(ConfigNames.GATEWAY_URL, ""));
    	}
    	String token;
    	try(Client client = ClientUtil.newClient(new URI(ConfigUtil.property(ConfigNames.SECURITY_URL)))){
    		token = Security.login(client, unit, username, PasswordHash.hash(username, password.toCharArray()), gatewayUrl);
    	}
    	return token;
    }
    
    public static void logOut(String unit, String token) throws Exception {
    	try(Client client = ClientUtil.newClient(new URI(ConfigUtil.property(ConfigNames.SECURITY_URL)))){
    		client.authorization(token);
    		token = Security.logOut(client, unit);
    	}
    }
}
