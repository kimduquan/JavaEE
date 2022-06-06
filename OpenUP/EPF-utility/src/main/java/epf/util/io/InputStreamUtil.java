package epf.util.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 
 */
public interface InputStreamUtil {

	/**
	 * @param input
	 * @param charset
	 * @param separator
	 * @return
	 * @throws Exception
	 */
	static String readString(final InputStream input, final String charset, final CharSequence separator) throws Exception {
		try(InputStreamReader reader = new InputStreamReader(input, charset)){
			try(BufferedReader bufferReader = new BufferedReader(reader)){
				return bufferReader.lines().collect(Collectors.joining(separator));
			}
		}
	}
}
