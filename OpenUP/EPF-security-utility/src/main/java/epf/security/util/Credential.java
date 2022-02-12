package epf.security.util;

import java.util.Optional;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;

/**
 * @author PC
 *
 */
public class Credential extends UsernamePasswordCredential {

	/**
	 * 
	 */
	private final Optional<String> ternant;

	/**
	 * @param ternant
	 * @param callerName
	 * @param password
	 */
	public Credential(final String ternant, final String callerName, final Password password) {
		super(callerName, password);
		this.ternant = Optional.ofNullable(ternant);
	}
	
	public Optional<String> getTernant() {
		return ternant;
	}
}
