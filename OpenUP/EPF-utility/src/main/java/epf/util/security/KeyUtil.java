package epf.util.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author PC
 *
 */
public interface KeyUtil {

	/**
	 * @param algorithm
	 * @param privateText
	 * @param decoder
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	static PrivateKey generatePrivate(final String algorithm, final String privateText, final Base64.Decoder decoder, final String charset) throws GeneralSecurityException, IOException {
		return KeyFactory.getInstance(algorithm)
                    .generatePrivate(
                            new PKCS8EncodedKeySpec(
                                decoder.decode(privateText.getBytes(charset))
                            )
                    );
	}
	
	/**
	 * @param algorithm
	 * @param publicText
	 * @param decoder
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	static PublicKey generatePublic(final String algorithm, final String publicText, final Base64.Decoder decoder, final String charset) throws GeneralSecurityException, IOException {
		return KeyFactory.getInstance(algorithm)
                    .generatePublic(
                            new X509EncodedKeySpec(
                                decoder.decode(publicText.getBytes(charset))
                            )
                    );
	}
}
