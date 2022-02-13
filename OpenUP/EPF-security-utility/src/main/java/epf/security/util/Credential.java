package epf.security.util;

import java.util.Optional;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;

/**
 * @author PC
 *
 */
public class Credential {

	/**
	 * 
	 */
	private final Optional<String> ternant;
	
	/**
	 * 
	 */
	private transient UsernamePasswordCredential credential;

	/**
	 * @param ternant
	 * @param callerName
	 * @param password
	 */
	public Credential(final String ternant, final String callerName, final Password password) {
		credential = new UsernamePasswordCredential(callerName, password);
		this.ternant = Optional.ofNullable(ternant);
	}
	
	public Optional<String> getTernant() {
		return ternant;
	}
	
	public String getCaller() {
		return credential.getCaller();
	}
	
	public Password getPassword() {
		return credential.getPassword();
	}
	
	public void setPassword(final Password password) {
		credential = new UsernamePasswordCredential(credential.getCaller(), password);
	}
}
