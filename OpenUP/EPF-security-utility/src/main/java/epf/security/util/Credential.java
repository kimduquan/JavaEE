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
	private final Optional<String> tenant;
	
	/**
	 * 
	 */
	private transient UsernamePasswordCredential credential;

	/**
	 * @param tenant
	 * @param callerName
	 * @param password
	 */
	public Credential(final String tenant, final String callerName, final Password password) {
		credential = new UsernamePasswordCredential(callerName, password);
		this.tenant = Optional.ofNullable(tenant);
	}
	
	public Optional<String> getTenant() {
		return tenant;
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
