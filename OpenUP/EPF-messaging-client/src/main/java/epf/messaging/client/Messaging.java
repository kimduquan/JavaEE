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
	 * @param uri
	 * @return
	 * @throws DeploymentException
	 * @throws IOException
	 */
	static Client connectToServer(final URI uri) throws DeploymentException, IOException {
		final Client client = new Client();
		client.setSession(ContainerProvider.getWebSocketContainer().connectToServer(client, uri));
		return client;
	}
}
