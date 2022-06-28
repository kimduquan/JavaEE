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
    private final Token token;
    
    /**
     * 
     */
    private transient final Credential credential;

    /**
     * @param token
     * @param credential
     */
    public Session(final Token token, final Credential credential) {
		this.token = token;
		this.credential = credential;
    }

	public Token getToken() {
		return token;
	}
   
    public Credential getCredential() {
		return credential;
	}
}
