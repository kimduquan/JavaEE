package epf.util.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
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
	 * @param encoder
	 * @return
	 * @throws Exception
	 */
	static String hash(final String string, final Encoder encoder) throws Exception {
		return StringUtil.encode(CryptoUtil.hash(string.getBytes("UTF-8")), encoder);
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
	
	/**
	 * @param string
	 * @param secretKey
	 * @param encoder
	 * @return
	 * @throws Exception
	 */
	static String encrypt(final String string, final SecretKey secretKey, final Encoder encoder) throws Exception {
		final byte[] bytes = string.getBytes("UTF-8");
		final byte[] encryptBytes = CryptoUtil.encrypt(bytes, secretKey);
		return encoder.encodeToString(encryptBytes);
	}
	
	/**
	 * @param string
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	static String encrypt(final String string, final SecretKey secretKey) throws Exception {
		return encrypt(string, secretKey, Base64.getUrlEncoder());
	}
	
	/**
	 * @param string
	 * @param secretKey
	 * @param decoder
	 * @return
	 * @throws Exception
	 */
	static String decrypt(final String string, final SecretKey secretKey, final Decoder decoder) throws Exception {
		final byte[] bytes = decoder.decode(string);
		final byte[] decryptBytes = CryptoUtil.decrypt(bytes, secretKey);
		return new String(decryptBytes, "UTF-8");
	}
	
	/**
	 * @param string
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	static String decrypt(final String string, final SecretKey secretKey) throws Exception {
		return decrypt(string, secretKey, Base64.getUrlDecoder());
	}
}
