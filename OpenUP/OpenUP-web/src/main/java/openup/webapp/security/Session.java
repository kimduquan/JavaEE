/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import javax.security.enterprise.credential.UsernamePasswordCredential;

/**
 *
 * @author FOXCONN
 */
public class Session {
    
    private UsernamePasswordCredential credential;
    private TokenPrincipal principal;

    public Session(UsernamePasswordCredential credential, TokenPrincipal principal) {
        this.credential = credential;
        this.principal = principal;
    }

    public UsernamePasswordCredential getCredential() {
        return credential;
    }

    public void setCredential(UsernamePasswordCredential credential) {
        this.credential = credential;
    }

    public TokenPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(TokenPrincipal principal) {
        this.principal = principal;
    }
}
