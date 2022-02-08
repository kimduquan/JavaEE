package epf.security.internal;

import epf.security.schema.Token;
import epf.security.util.JPAPrincipal;

/**
 *
 * @author FOXCONN
 */
public class Session implements AutoCloseable {
   
    /**
     * 
     */
    private transient final JPAPrincipal principal;
    
    /**
     * 
     */
    private final Token token;

    /**
     * @param principal
     * @param token
     */
    public Session(final JPAPrincipal principal, final Token token) {
    	this.principal = principal;
		this.token = token;
    }

	public Token getToken() {
		return token;
	}

	public JPAPrincipal getPrincipal() {
		return principal;
	}

	@Override
	public void close() throws Exception {
		principal.close();
	}
}
