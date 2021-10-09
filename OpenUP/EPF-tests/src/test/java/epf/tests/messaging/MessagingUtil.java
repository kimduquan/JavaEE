/**
 * 
 */
package epf.tests.messaging;

import java.net.URI;
import java.net.URISyntaxException;

import epf.messaging.client.Messaging;

/**
 * @author PC
 *
 */
public class MessagingUtil {

	private static URI messagingUri;
	
	public static URI getMessagingUrl() throws URISyntaxException {
		if(messagingUri == null) {
			messagingUri = new URI(System.getProperty(Messaging.MESSAGING_URL));
		}
		return messagingUri;
	}
}
