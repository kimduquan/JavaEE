package epf.util.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import epf.util.StringUtil;

/**
 * 
 */
public interface SecurityUtil {

	/**
	 * @param string
	 * @return
	 * @throws Exception
	 */
	static String hash(final String string) throws Exception {
		return StringUtil.encode(CryptoUtil.hash(string.getBytes("UTF-8")));
	}
	
	/**
	 * @param string
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	static String encrypt(final String string, final StringBuilder data, final PublicKey publicKey) throws Exception {
		return StringUtil.encode(encrypt(string.getBytes("UTF-8"), data, publicKey));
	}
	
	/**
	 * @param bytes
	 * @param publicKey
	 * @param data
	 * @return
	 * @throws Exception
	 */
	static byte[] encrypt(final byte[] bytes, final StringBuilder string, final PublicKey publicKey) throws Exception {
		final byte[] hashBytes = CryptoUtil.hash(bytes);
		final SecureRandom random = new SecureRandom(hashBytes);
		final SecretKey secretKey = KeyUtil.generateSecret("AES", 128, random);
		final byte[] encryptBytes = CryptoUtil.encrypt(bytes, secretKey);
		final String encryptString = StringUtil.encode(encryptBytes);
		string.append(encryptString);
		final byte[] encryptKeyBytes = CryptoUtil.encrypt(secretKey.getEncoded(), publicKey);
		return encryptKeyBytes;
	}
	
	/**
	 * @param encryptKey
	 * @param encryptString
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	static String decrypt(final String encryptKey, final String encryptString, final PrivateKey privateKey) throws Exception {
		return new String(decrypt(StringUtil.decode(encryptKey), encryptString, privateKey), "UTF-8");
	}
	
	/**
	 * @param encryptKeyBytes
	 * @param encryptString
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	static byte[] decrypt(final byte[] encryptKeyBytes, final String encryptString, final PrivateKey privateKey) throws Exception {
		final byte[] encryptStringBytes = StringUtil.decode(encryptString);
		final byte[] secretKeyBytes = CryptoUtil.decrypt(encryptKeyBytes, privateKey);
		final SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
		final byte[] stringBytes = CryptoUtil.decrypt(encryptStringBytes, secretKey);
		return stringBytes;
	}
}
