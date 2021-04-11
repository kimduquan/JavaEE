/**
 * 
 */
package epf.service;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
public class CacheUtil {
	
	public static Client connectToServer() throws DeploymentException, IOException, URISyntaxException {
		return Client.connectToServer(
				ContainerProvider.getWebSocketContainer(), 
				MessagingUtil.getMessagingUrl().resolve("cache")
				);
	}

}
