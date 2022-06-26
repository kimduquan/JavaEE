package epf.security.internal;

import epf.security.schema.Token;
import epf.security.util.Credential;

/**
 *
 * @author FOXCONN
 */
public class Session {

	/**
     * 
     */
    private transient final JPAPrincipal principal;
    
    /**
     * 
     */
    private final Token token;
    
    /**
     * 
     */
    private transient final Credential credential;

    /**
     * @param principal
     * @param token
     * @param credential
     */
    public Session(final JPAPrincipal principal, final Token token, final Credential credential) {
    	this.principal = principal;
		this.token = token;
		this.credential = credential;
    }

	public Token getToken() {
		return token;
	}

	public JPAPrincipal getPrincipal() {
		return principal;
	}
   
    public Credential getCredential() {
		return credential;
	}
}
