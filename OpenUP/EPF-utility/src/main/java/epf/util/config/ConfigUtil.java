/**
 * 
 */
package epf.util.config;

import java.net.URI;
import java.net.URISyntaxException;
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
		String value = SystemUtil.getenv(name);
		if(value == null) {
			value = System.getProperty(name);
		}
		return value;
	}
	
	/**
	 * @param name
	 * @return
	 * @throws URISyntaxException 
	 * @throws Exception 
	 */
	static URI getURI(final String name) throws URISyntaxException {
		return new URI(getString(name));
	}
}
