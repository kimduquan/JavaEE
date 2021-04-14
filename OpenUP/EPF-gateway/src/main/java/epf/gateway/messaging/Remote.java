/**
 * 
 */
package epf.gateway.messaging;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import epf.util.websocket.Server;

/**
 * @author PC
 *
 */
@ClientEndpoint(decoders = {MessageDecoder.class})
public class Remote implements AutoCloseable {
	
	/**
	 * 
	 */
	private transient final Session session;
	
	/**
	 * 
	 */
	private transient final Server server;
	
	/**
	 * @param message
	 */
	@OnMessage
	public void onMessage(final Message message) {
		server.forEach(session -> session.getAsyncRemote().sendText(message.getText()));
	}
	
	/**
	 * @param container
	 * @param uri
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public Remote(final WebSocketContainer container, final URI uri) throws DeploymentException, IOException {
		session = container.connectToServer(this, uri);
		this.server = new Server();
	}

	@Override
	public void close() throws Exception {
		session.close();
		server.close();
	}
	
	public Server getServer() {
		return server;
	}
}
