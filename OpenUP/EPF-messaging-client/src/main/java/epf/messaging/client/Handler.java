/**
 * 
 */
package epf.messaging.client;

import java.net.URI;
import java.util.Queue;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;

/**
 * @author PC
 *
 */
public class Handler implements SendHandler, AutoCloseable {
	
	/**
	 * 
	 */
	private transient final Queue<Client> clients;
	
	/**
	 * 
	 */
	private transient final boolean async;
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * @param clients
	 * @param url
	 * @throws Exception
	 */
	public Handler(final Queue<Client> clients, final boolean async, final URI url) throws Exception {
		this.clients = clients;
		this.client = clients.poll();
		if(client == null) {
			client = Messaging.connectToServer(url);
		}
		this.async = async;
	}

	@Override
	public void onResult(final SendResult result) {
		clients.add(client);
	}

	@Override
	public void close() throws Exception {
		if(!async) {
			clients.add(client);
		}
	}
	
	/**
	 * @param object
	 * @param async
	 * @throws Exception
	 */
	public void sendObject(final Object object) throws Exception {
		if(async) {
			client.getSession().getAsyncRemote().sendObject(object, this);
		}
		else {
			client.getSession().getBasicRemote().sendObject(object);
		}
	}
}
