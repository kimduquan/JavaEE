package epf.webapp.security;

import java.util.Optional;
import javax.security.enterprise.CallerPrincipal;

/**
 * @author PC
 *
 */
public class TokenPrincipal extends CallerPrincipal {
	
	/**
	 * 
	 */
	private final String rawToken;
	
	/**
	 * 
	 */
	private Optional<String> rememberToken = Optional.empty();

	/**
	 * @param name
	 * @param token
	 */
	public TokenPrincipal(final String name, final String rawToken) {
		super(name);
		this.rawToken = rawToken;
	}

	public String getRawToken() {
		return rawToken;
	}
	
	/**
	 * @param token
	 */
	public void setRememberToken(final String token) {
		rememberToken = Optional.of(token);
	}
	
	/**
	 * @return
	 */
	public Optional<String> getRememberToken(){
		return this.rememberToken;
	}
}
