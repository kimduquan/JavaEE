/**
 * 
 */
package epf.service;

import java.net.URI;
import java.net.URISyntaxException;
import epf.client.config.ConfigNames;

/**
 * @author PC
 *
 */
public class MessagingUtil {

	private static URI messagingUri;
	
	public static URI getMessagingUrl() throws URISyntaxException {
		if(messagingUri == null) {
			messagingUri = new URI(System.getProperty(ConfigNames.MESSAGING_URL));
		}
		return messagingUri;
	}
}
