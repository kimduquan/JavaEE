package epf.webapp.internal;

import java.util.Map;
import jakarta.security.enterprise.CallerPrincipal;

public class TokenPrincipal extends CallerPrincipal {
	
	private transient final char[] rawToken;
	
	private transient char[] rememberToken;
	
	private transient final Map<String, Object> claims;

	public TokenPrincipal(final String name, final char[] rawToken, final Map<String, Object> claims) {
		super(name);
		this.rawToken = rawToken;
		this.claims = claims;
	}

	public char[] getRawToken() {
		return rawToken;
	}

	public Map<String, Object> getClaims() {
		return claims;
	}
	
	public void setRememberToken(final char[] token) {
		rememberToken = token;
	}
	
	public char[] getRememberToken(){
		return this.rememberToken;
	}
}
