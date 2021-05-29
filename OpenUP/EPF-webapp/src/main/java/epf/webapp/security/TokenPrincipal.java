/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.security;

import javax.security.enterprise.CallerPrincipal;
import epf.client.security.Token;

/**
 *
 * @author FOXCONN
 */
public class TokenPrincipal extends CallerPrincipal {
    
    /**
     * 
     */
    private Token token;
    
    /**
     * @param name
     * @param token
     */
    public TokenPrincipal(final Token token){
        super(token.getTokenID());
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(final Token token) {
        this.token = token;
    }
}
