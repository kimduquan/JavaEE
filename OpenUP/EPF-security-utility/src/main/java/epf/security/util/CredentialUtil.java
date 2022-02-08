package epf.security.util;

import java.util.Objects;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public interface CredentialUtil {
	
	/**
	 * @param credential
	 * @param ternant
	 * @return
	 */
	static String getUsername(final UsernamePasswordCredential credential, final StringBuilder ternant) {
		Objects.requireNonNull(credential, "UsernamePasswordCredential");
		Objects.requireNonNull(credential.getCaller(), "UsernamePasswordCredential.caller");
		final String[] segments = credential.getCaller().split(Naming.Security.Internal.USERNAME_TERNANT_SEPARATOR);
		String username = "";
		if(segments.length > 0) {
			username = segments[0];
		}
		if(segments.length > 1) {
			ternant.append(segments[1]);
		}
		return username;
	}
	
	/**
	 * @param ternant
	 * @param username
	 * @param password
	 * @return
	 */
	static UsernamePasswordCredential newTernantCredential(final String ternant, final String username, final Password password) {
		Objects.requireNonNull(password, "Password");
		return new UsernamePasswordCredential(username + Naming.Security.Internal.USERNAME_TERNANT_SEPARATOR + ternant, password);
	}
}
