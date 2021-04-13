/**
 * 
 */
package epf.client.messaging;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;

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
	static Client connectToServer(final URI uri, final String path) throws DeploymentException, IOException {
		final Client client = new Client();
		final Session session = ContainerProvider.getWebSocketContainer().connectToServer(client, uri.resolve(path));
		client.setSession(session);
		return client;
	}
	
	void sendObject(String path, Object object);
}
