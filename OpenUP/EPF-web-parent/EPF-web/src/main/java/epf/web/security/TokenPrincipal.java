/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.web.security;

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
     * 
     */
    private String caller;
    
    /**
     * @param name
     * @param token
     */
    public TokenPrincipal(final String caller, final Token token){
        super(token.getTokenID());
        this.token = token;
        this.caller = caller;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(final Token token) {
        this.token = token;
    }

	public String getCaller() {
		return caller;
	}
}
