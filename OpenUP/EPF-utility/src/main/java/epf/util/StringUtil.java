/**
 * 
 */
package epf.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
	 * 
	 */
	String NULL = "\0";

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
		Objects.requireNonNull(shortString, "String");
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
	
	/**
	 * @param strings
	 * @return
	 */
	static String join(final String...strings) {
		return String.join(NULL, strings);
	}
	
	/**
	 * @param string
	 * @return
	 */
	static String[] split(final String string) {
		return string.split(NULL);
	}
	
	/**
	 * @param collection
	 * @param separator
	 * @return
	 */
	static String valueOf(final Collection<?> collection, final String separator) {
		return collection.stream().map(String::valueOf).collect(Collectors.joining(separator));
	}
	
	/**
	 * @param string
	 * @param size
	 * @return
	 */
	static List<String> split(final String string, final int size) {
		final List<String> strings = new ArrayList<>();
		for(int start = 0, end = size; end <= string.length(); start = end, end += size) {
			strings.add(string.substring(start, end));
			if(end + size > string.length()) {
				strings.add(string.substring(end));
			}
		}
		return strings;
	}
}
