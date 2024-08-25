package epf.util.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * @author PC
 *
 */
public interface KeySpecUtil {
	
	/**
	 * 
	 */
	String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
	
	/**
	 * 
	 */
	String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
	
	/**
	 * 
	 */
	String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
	
	/**
	 * 
	 */
	String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
	
	/**
	 * @param bytes
	 * @param string
	 * @param begin
	 * @param end
	 * @throws Exception
	 */
	static void encodePKCS8(final byte[] bytes, final StringBuilder string, final String begin, final String end) throws Exception {
		final byte[] lineSeparator = System.lineSeparator().getBytes("UTF-8");
		final Encoder encoder = Base64.getMimeEncoder(64, lineSeparator);
		string.append(begin);
		string.append(System.lineSeparator());
		string.append(encoder.encodeToString(bytes));
		string.append(System.lineSeparator());
		string.append(end);
	}

	/**
	 * @param privateKey
	 * @param string
	 * @throws Exception
	 */
	static void encodePKCS8(final PrivateKey privateKey, final StringBuilder string) throws Exception {
		encodePKCS8(privateKey.getEncoded(), string, BEGIN_PRIVATE_KEY, END_PRIVATE_KEY);
	}
	
	/**
	 * @param publicKey
	 * @param string
	 * @throws Exception
	 */
	static void encodePKCS8(final PublicKey publicKey, final StringBuilder string) throws Exception {
		encodePKCS8(publicKey.getEncoded(), string, BEGIN_PUBLIC_KEY, END_PUBLIC_KEY);
	}
	
	/**
	 * @param bytes
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	static byte[] decodePKCS8(final byte[] bytes, final String begin, final String end) throws Exception {
		final String string = new String(bytes, "UTF-8");
		final String encodedString = string.substring(begin.length() + System.lineSeparator().length(), string.length() - System.lineSeparator().length() - end.length());
		final Decoder decoder = Base64.getMimeDecoder();
		return decoder.decode(encodedString.getBytes("UTF-8"));
	}
}
