package epf.util.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author PC
 *
 */
public interface KeyUtil {
	
	/**
	 * @param algorithm
	 * @param keySize
	 * @return
	 * @throws GeneralSecurityException
	 */
	static SecretKey generateSecret(final String algorithm, final int keySize) throws GeneralSecurityException {
		final KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(keySize);
		return keyGenerator.generateKey();
	}
	
	/**
	 * @param algorithm
	 * @param keySize
	 * @param random
	 * @return
	 * @throws GeneralSecurityException
	 */
	static SecretKey generateSecret(final String algorithm, final int keySize, final SecureRandom random) throws GeneralSecurityException {
		final KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(keySize, random);
		return keyGenerator.generateKey();
	}
	
	/**
	 * @param algorithm
	 * @param keySize
	 * @param privateKey
	 * @param publicKey
	 * @throws Exception
	 */
	static void generateKeyPair(final String algorithm, final int keySize, final StringBuilder privateKey, final StringBuilder publicKey) throws Exception {
		final KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
		generator.initialize(keySize);
		final KeyPair keyPair = generator.generateKeyPair();
		KeySpecUtil.encodePKCS8(keyPair.getPrivate(), privateKey);
		KeySpecUtil.encodePKCS8(keyPair.getPublic(), publicKey);
	}
	
	/**
	 * @param algorithm
	 * @param keySize
	 * @param privateKey
	 * @param publicKey
	 * @throws Exception
	 */
	static void generateKeyPair(final String algorithm, final int keySize, final Path privateKey, final Path publicKey) throws Exception {
		final StringBuilder privateKeyText = new StringBuilder();
		final StringBuilder publicKeyText = new StringBuilder();
		generateKeyPair(algorithm, keySize, privateKeyText, publicKeyText);
		Files.write(privateKey, privateKeyText.toString().getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
		Files.write(publicKey, publicKeyText.toString().getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
	}
	
	/**
	 * @param algorithm
	 * @param string
	 * @return
	 * @throws Exception
	 */
	static PrivateKey decodePrivateKey(final String algorithm, final byte[] bytes) throws Exception {
		final KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
	    final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(KeySpecUtil.decodePKCS8(bytes, KeySpecUtil.BEGIN_PRIVATE_KEY, KeySpecUtil.END_PRIVATE_KEY));
	    return keyFactory.generatePrivate(keySpec);
	}
	
	/**
	 * @param algorithm
	 * @param string
	 * @return
	 * @throws Exception
	 */
	static PublicKey decodePublicKey(final String algorithm, final byte[] bytes) throws Exception {
		final KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
	    final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(KeySpecUtil.decodePKCS8(bytes, KeySpecUtil.BEGIN_PUBLIC_KEY, KeySpecUtil.END_PUBLIC_KEY));
	    return keyFactory.generatePublic(keySpec);
	}
	
	/**
	 * @param secretKey
	 * @param encoder
	 * @return
	 */
	static String encodeToString(final SecretKey secretKey, final Encoder encoder) {
		return encoder.encodeToString(secretKey.getEncoded());
	}
	
	/**
	 * @param secretKey
	 * @return
	 */
	static String encodeToString(final SecretKey secretKey) {
		return encodeToString(secretKey, Base64.getUrlEncoder());
	}
	
	/**
	 * @param secret
	 * @param decoder
	 * @param algorithm
	 * @return
	 */
	static SecretKey decodeFromString(final String secret, final Decoder decoder, final String algorithm) {
		final byte[] bytes = decoder.decode(secret);
		return new SecretKeySpec(bytes, 0, bytes.length, algorithm);
	}
	
	/**
	 * @param secret
	 * @return
	 */
	static SecretKey decodeFromString(final String secret) {
		return decodeFromString(secret, Base64.getUrlDecoder(), "AES");
	}
}
