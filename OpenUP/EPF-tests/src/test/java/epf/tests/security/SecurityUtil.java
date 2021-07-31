/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/service/SecurityUtil.java
package epf.tests.service;

import epf.client.security.Security;
import epf.tests.client.ClientUtil;
=======
package epf.tests.security;

import epf.client.security.Security;
import epf.client.security.Token;
import epf.tests.client.ClientUtil;
import epf.tests.registry.RegistryUtil;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/security/SecurityUtil.java
import epf.util.client.Client;
import epf.util.logging.Logging;
import epf.util.security.PasswordUtil;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FOXCONN
 */
public class SecurityUtil {
    
	private static final Logger logger = Logging.getLogger(SecurityUtil.class.getName());
    private static URI securityUrl;
    
    public static String login(String username, String password) {
    	if(securityUrl == null) {
    		securityUrl = RegistryUtil.lookup("security", null);
    	}
    	String token = null;
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		token = Security.login(client, username, PasswordUtil.hash(username, password.toCharArray()), securityUrl.toURL());
    	}
    	catch(Exception ex) {
    		logger.log(Level.SEVERE, "login", ex);
    	}
    	return token;
    }
    
    public static void logOut(String token) {
    	try(Client client = ClientUtil.newClient(RegistryUtil.lookup("security", null))){
    		client.authorization(token);
    		token = Security.logOut(client);
    	}
    	catch(Exception ex) {
    		logger.log(Level.SEVERE, "logOut", ex);
    	}
    }
    
    public static Token auth(String token) throws Exception {
    	try(Client client = ClientUtil.newClient(RegistryUtil.lookup("security", null))){
    		client.authorization(token);
    		return Security.authenticate(client);
    	}
    }
    
    public static String revoke(String token) throws Exception {
    	try(Client client = ClientUtil.newClient(RegistryUtil.lookup("security", null))){
    		client.authorization(token);
    		return Security.revoke(client);
    	}
    }
}
