package epf.security.util;

import java.nio.charset.StandardCharsets;
import jakarta.security.enterprise.credential.Password;
import epf.util.StringUtil;

public interface CredentialUtil {

	static Credential newCredential(final String tenant, final String username, final String passwordText) throws Exception {
		final String encryptPassword = encryptPassword(username, passwordText);
    	final Password password = new Password(encryptPassword);
    	final Credential credential = new Credential(tenant, username, password);
    	return credential;
	}
	
	static String encryptPassword(final String username, final String passwordText) throws Exception {
		final byte[] passwordBytes = PasswordUtil.getPasswordHash(username.toUpperCase(), passwordText.toCharArray(), "SHA-256");
    	final String passwordHash = StringUtil.toHex(passwordBytes, StandardCharsets.ISO_8859_1);
    	return CryptoUtil.encrypt(passwordHash);
	}
}
