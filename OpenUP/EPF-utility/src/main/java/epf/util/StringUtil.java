/**
 * 
 */
package epf.util;

import java.time.Instant;
import java.util.UUID;

/**
 * @author PC
 *
 */
public interface StringUtil {
	
	/**
	 * 
	 */
	char[] SHORT_STRING_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	/**
	 * 
	 */
	int INDEX_OF_A = String.valueOf(SHORT_STRING_CHARS).indexOf('A');
	/**
	 * 
	 */
	int INDEX_OF_0 = String.valueOf(SHORT_STRING_CHARS).indexOf('0');

	/**
	 * @param string
	 * @return
	 */
	static String randomString(final String string) {
		return string + UUID.randomUUID() + Instant.now().toEpochMilli();
	}
	
	/**
	 * @param number
	 * @return
	 */
	static String toShortString(int number) {
		final StringBuffer shortString = new StringBuffer();
        while (number > 0)
        {
        	shortString.append(SHORT_STRING_CHARS[number % SHORT_STRING_CHARS.length]);
        	number = number / SHORT_STRING_CHARS.length;
        }
        return shortString.reverse().toString();
	}
	
	/**
	 * @param shortString
	 * @return
	 */
	static int fromShortString(final String shortString) {
		int number = 0;
		for (char ch : shortString.toCharArray())
        {
            if ('a' <= ch && ch <= 'z') {
            	number = number * SHORT_STRING_CHARS.length + ch - 'a';
            }
            if ('A' <= ch && ch <= 'Z') {
            	number = number * SHORT_STRING_CHARS.length + ch - 'A' + INDEX_OF_A;
            }
            if ('0' <= ch && ch <= '9') {
            	number = number * SHORT_STRING_CHARS.length + ch - '0' + INDEX_OF_0;
            }
        }
		return number;
	}
}
