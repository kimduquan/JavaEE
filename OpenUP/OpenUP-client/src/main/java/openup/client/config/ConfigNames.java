/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.config;

/**
 *
 * @author FOXCONN
 */
public interface ConfigNames {
    String MP_JWT_PRIVATE_KEY = "mp.jwt.issue.privatekey";
    String JWT_EXPIRE_DURATION = "openup.security.jwt.exp.duration";
    String JWT_EXPIRE_TIMEUNIT = "openup.security.jwt.exp.timeunit";
    
    String OPENUP_GATEWAY_URL = "openup.gateway.url";
    String OPENUP_PERSISTENCE_URL = "openup.persistence.url";
}
