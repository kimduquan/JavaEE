/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.config;

/**
 * ConfigNames
 * @author FOXCONN
 */
public interface ConfigNames {
	
    /**
     * 
     */
    String JWT_PRIVATE_KEY = "mp.jwt.issue.privatekey";
    /**
     * 
     */
    String JWT_EXPIRE_DURATION = "epf.security.jwt.exp.duration";
    /**
     * 
     */
    String JWT_EXPIRE_TIMEUNIT = "epf.security.jwt.exp.timeunit";
    /**
     * 
     */
    String WEB_APP_URL = "epf.webapp.url";
}
