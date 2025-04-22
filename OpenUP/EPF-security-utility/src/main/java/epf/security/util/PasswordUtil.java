package epf.security.util;

import java.security.MessageDigest;
import java.util.Arrays;

public interface PasswordUtil {

	static byte[] getHash(final byte[] data, final boolean nullData, final String algorithm) throws Exception {
		final byte[] result = MessageDigest.getInstance(algorithm).digest(data);
        if (nullData) {
            Arrays.fill(data, (byte) 0);
        }
        return result;
	}
	
	static byte[] getPasswordHash(final String userName, final char[] password, final String algorithm) throws Exception {
        final String user = userName + "@";
        final byte[] buff = new byte[2 * (user.length() + password.length)];
        int n = 0;
        for (int i = 0, length = user.length(); i < length; i++) {
            final char c = user.charAt(i);
            buff[n++] = (byte) (c >> 8);
            buff[n++] = (byte) c;
        }
        for (char c : password) {
            buff[n++] = (byte) (c >> 8);
            buff[n++] = (byte) c;
        }
        Arrays.fill(password, (char) 0);
        return getHash(buff, true, algorithm);
    }
}
