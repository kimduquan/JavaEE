/**
 * 
 */
package epf.util.security;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author PC
 *
 */
public interface KeyUtil {

	/**
	 * @param algorithm
	 * @param privateText
	 * @return
	 * @throws GeneralSecurityException
	 */
	static PrivateKey generatePrivate(final String algorithm, final String privateText) throws GeneralSecurityException {
		final Base64.Decoder decoder = Base64.getUrlDecoder();
        return KeyFactory.getInstance(algorithm)
                    .generatePrivate(
                            new PKCS8EncodedKeySpec(
                                decoder.decode(privateText)
                            )
                    );
	}
}
