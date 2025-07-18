package epf.webapp.security.auth;

public abstract class Credential implements jakarta.security.enterprise.credential.Credential {

	private transient final String sessionId;
	
	private transient final String provider;
	
	public Credential(final String provider, final String sessionId) {
		this.sessionId = sessionId;
		this.provider = provider;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getProvider() {
		return provider;
	}
}
