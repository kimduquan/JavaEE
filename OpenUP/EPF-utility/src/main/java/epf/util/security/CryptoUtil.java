package epf.util.security;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author PC
 *
 */
public interface CryptoUtil {

	/**
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	static String hash(final String value) throws Exception {
		final byte[] hash = MessageDigest.getInstance("SHA-256").digest(value.getBytes("UTF-8"));
		return new String(Base64.getEncoder().encode(hash), "UTF-8");
	}
}
