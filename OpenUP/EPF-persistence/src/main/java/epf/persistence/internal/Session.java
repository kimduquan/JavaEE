package epf.persistence.internal;

import epf.persistence.security.auth.EPFPrincipal;
import epf.security.schema.Token;

/**
 *
 * @author FOXCONN
 */
public class Session {
   
    /**
     * 
     */
    private transient final EPFPrincipal principal;
    
    /**
     * 
     */
    private final Token token;

    /**
     * @param principal
     */
    public Session(final EPFPrincipal principal, final Token token) {
    	this.principal = principal;
		this.token = token;
    }

	public Token getToken() {
		return token;
	}

	public EPFPrincipal getPrincipal() {
		return principal;
	}
}
