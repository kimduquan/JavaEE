package epf.util;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	
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
	
	/**
	 * @param words
	 * @return
	 */
	static String toCamelCase(final String... words) {
		final StringBuilder camelCase = new StringBuilder();
		if(words.length > 0) {
			final String firstWord = words[0];
			camelCase.append(firstWord.toLowerCase());
			for(int i = 1; i < words.length; i++) {
				final String word = words[i].toLowerCase();
				if(word.length() > 0) {
					camelCase.append(Character.toUpperCase(word.charAt(0)));
					camelCase.append(word.substring(1).toLowerCase());
				}
			}
		}
		return camelCase.toString();
	}
	
	/**
	 * @param words
	 * @return
	 */
	static String toPascalCase(final String... words) {
		final StringBuilder camelCase = new StringBuilder();
		for(int i = 0; i < words.length; i++) {
			final String word = words[i];
			if(word.length() > 0) {
				camelCase.append(Character.toUpperCase(word.charAt(0)));
				camelCase.append(word.substring(1).toLowerCase());
			}
		}
		return camelCase.toString();
	}
	
	/**
	 * @param words
	 * @return
	 */
	static String toKebabCase(final String... words) {
		return Stream.of(words).map(String::toLowerCase).collect(Collectors.joining("-"));
	}
	
	/**
	 * @param words
	 * @return
	 */
	static String toSnakeCase(final String... words) {
		return Stream.of(words).map(String::toLowerCase).collect(Collectors.joining("_"));
	}
	
	/**
	 * @param words
	 * @return
	 */
	static String toPascalSnakeCase(final String... words) {
		return Stream.of(words).map(word -> "" + Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase()).collect(Collectors.joining("_"));
	}
	
	/**
	 * @param value
	 * @param charset
	 * @return
	 */
	static String toHex(final byte[] value, final Charset charset) {
        final byte[] bytes = new byte[value.length * 2];
        final char[] hex = HEX_CHARS;
        for (int i = 0, j = 0; i < value.length; i++) {
            final int c = value[i] & 0xff;
            bytes[j++] = (byte) hex[c >> 4];
            bytes[j++] = (byte) hex[c & 0xf];
        }
        return new String(bytes, charset);
    }
}
