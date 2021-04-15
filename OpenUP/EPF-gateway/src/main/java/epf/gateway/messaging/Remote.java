/**
 * 
 */
package epf.gateway.messaging;

import java.io.IOException;
import java.net.URI;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import epf.util.websocket.Client;
import epf.util.websocket.Server;

/**
 * @author PC
 *
 */
public class Remote implements AutoCloseable {
	
	/**
	 * 
	 */
	private transient final Client client;
	
	/**
	 * 
	 */
	private transient final Server server;
	
	/**
	 * @param message
	 */
	protected void onMessage(final String message) {
		server.forEach(session -> session.getAsyncRemote().sendText(message));
	}
	
	/**
	 * @param container
	 * @param uri
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public Remote(final WebSocketContainer container, final URI uri) throws DeploymentException, IOException {
		client = Client.connectToServer(container, uri);
		this.server = new Server();
		client.onMessage(this::onMessage);
	}

	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}
	
	public Server getServer() {
		return server;
	}
}
