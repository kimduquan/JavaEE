package epf.util.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import epf.util.SystemUtil;

/**
 * @author PC
 *
 */
public interface ConfigUtil {

	/**
	 * @param name
	 * @return
	 */
	static String getString(final String name) {
		return SystemUtil.getString(name);
	}
	
	/**
	 * @param name
	 * @return
	 * @throws URISyntaxException 
	 * @throws Exception 
	 */
	static URI getURI(final String name) throws URISyntaxException {
		return new URI(SystemUtil.getString(name));
	}
	
	static URL getURL(final String name) throws MalformedURLException {
		return URI.create(SystemUtil.getString(name)).toURL();
	}
	
	/**
	 * @param name
	 * @return
	 */
	static Path getPath(final String name) {
		return Paths.get(SystemUtil.getString(name));
	}
	
	/**
	 * @param name
	 * @return
	 */
	static char[] getChars(final String name) {
		return SystemUtil.getString(name).toCharArray();
	}
	
	/**
	 * @param name
	 * @return
	 */
	static Long getLong(final String name) {
		return Long.valueOf(SystemUtil.getString(name));
	}
	
	/**
	 * @param name
	 * @return
	 */
	static Duration getDuration(final String name) {
		return Duration.parse(SystemUtil.getString(name));
	}
}
