/**
 * 
 */
package epf.service;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.websocket.DeploymentException;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;

/**
 * @author PC
 *
 */
public class CacheUtil {
	
	public static Client connectToServer() throws DeploymentException, IOException, URISyntaxException {
		return Messaging.connectToServer(MessagingUtil.getMessagingUrl().resolve("cache"));
	}
}
