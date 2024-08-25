package epf.util.security;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * @author PC
 *
 */
public interface CryptoUtil {
	
	/**
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	static byte[] hash(final byte[] bytes) throws Exception {
		return MessageDigest.getInstance("SHA-256").digest(bytes);
	}
	
	/**
	 * @param bytes
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	static byte[] encrypt(final byte[] bytes, final PublicKey publicKey) throws Exception {
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(bytes);
	}
	
	/**
	 * @param bytes
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	static byte[] decrypt(final byte[] bytes, final PrivateKey privateKey) throws Exception {
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(bytes);
	}
	
	/**
	 * @param bytes
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	static byte[] encrypt(final byte[] bytes, final SecretKey secretKey) throws Exception {
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}
	
	/**
	 * @param bytes
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	static byte[] decrypt(final byte[] bytes, final SecretKey secretKey) throws Exception {
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}
}
