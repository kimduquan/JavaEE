package epf.security.util;

import org.eclipse.persistence.internal.security.JCEEncryptor;

public class CryptoUtil {

	private static transient JCEEncryptor JCE;
	
	public static String encrypt(final String text) throws Exception {
		if(JCE == null) {
			JCE = new JCEEncryptor();
		}
		return JCE.encryptPassword(text);
	}
}
