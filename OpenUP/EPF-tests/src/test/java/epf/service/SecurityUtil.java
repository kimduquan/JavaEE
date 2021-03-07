/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service;

import epf.client.security.Security;
import epf.util.client.Client;
import epf.util.security.PasswordHash;
import java.net.URI;

/**
 *
 * @author FOXCONN
 */
public class SecurityUtil {
    
    private static URI securityUrl;
    
    public static String login(String unit, String username, String password) throws Exception {
    	if(securityUrl == null) {
    		securityUrl = RegistryUtil.lookup("security");
    	}
    	String token;
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		token = Security.login(client, unit, username, PasswordHash.hash(username, password.toCharArray()), securityUrl.toURL());
    	}
    	return token;
    }
    
    public static void logOut(String unit, String token) throws Exception {
    	try(Client client = ClientUtil.newClient(RegistryUtil.lookup("security"))){
    		client.authorization(token);
    		token = Security.logOut(client, unit);
    	}
    }
}
