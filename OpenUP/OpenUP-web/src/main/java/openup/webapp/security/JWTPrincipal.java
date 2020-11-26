/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import javax.security.enterprise.CallerPrincipal;

/**
 *
 * @author FOXCONN
 */
public class JWTPrincipal extends CallerPrincipal {
    
    private String token;
    
    public JWTPrincipal(String name){
        super(name);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
