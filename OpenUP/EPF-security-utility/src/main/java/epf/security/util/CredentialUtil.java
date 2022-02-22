package epf.security.util;

import java.nio.charset.StandardCharsets;
import javax.security.enterprise.credential.Password;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public interface CredentialUtil {

	/**
	 * @param tenant
	 * @param username
	 * @param passwordText
	 * @return
	 * @throws Exception
	 */
	static Credential newCredential(final String tenant, final String username, final String passwordText) throws Exception {
		final byte[] passwordBytes = PasswordUtil.getPasswordHash(username.toUpperCase(), passwordText.toCharArray(), "SHA-256");
    	final String passwordHash = StringUtil.toHex(passwordBytes, StandardCharsets.ISO_8859_1);
    	final String encryptPassword = CryptoUtil.encrypt(passwordHash);
    	final Password password = new Password(encryptPassword);
    	final Credential credential = new Credential(tenant, username, password);
    	return credential;
	}
}
