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
public class TokenPrincipal extends CallerPrincipal {
    
    private Token token;
    
    public TokenPrincipal(String name){
        super(name);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
