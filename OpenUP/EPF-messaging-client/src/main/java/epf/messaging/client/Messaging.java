/**
 * 
 */
package epf.messaging.client;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;

/**
 * @author PC
 *
 */
public interface Messaging {
	
    /**
     * 
     */
    String MESSAGING_URL = "epf.messaging.url";

	/**
	 * @param uri
	 * @return
	 * @throws DeploymentException
	 * @throws IOException
	 */
	static Client connectToServer(URI uri) throws DeploymentException, IOException {
		final Client client = new Client();
		client.setSession(ContainerProvider.getWebSocketContainer().connectToServer(client, uri));
		return client;
	}
}
