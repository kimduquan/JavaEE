package epf.webapp.internal;

import jakarta.security.enterprise.credential.Credential;

public class TokenCredential implements Credential {

	private transient final char[] token;
	
	public TokenCredential(final char[] token) {
		this.token = token;
	}

	public char[] getToken() {
		return token;
	}
}
