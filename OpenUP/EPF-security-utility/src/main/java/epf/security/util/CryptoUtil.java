package epf.security.util;

import org.eclipse.persistence.internal.security.JCEEncryptor;

/**
 * @author PC
 *
 */
public class CryptoUtil {

	/**
	 * 
	 */
	private static transient JCEEncryptor JCE;
	
	/**
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(final String text) throws Exception {
		if(JCE == null) {
			JCE = new JCEEncryptor();
		}
		return JCE.encryptPassword(text);
	}
}
