package epf.util.net;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author PC
 *
 */
public interface URIUtil {

	/**
	 * @param uri
	 * @return
	 */
	static URI parse(final String uri) {
		try {
			return new URI(uri);
		} 
		catch (URISyntaxException e) {
			return null;
		}
	}
}
