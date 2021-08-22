/**
 * 
 */
package epf.persistence.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import epf.client.messaging.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class MessageQueue implements Runnable, Closeable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(MessageQueue.class.getName());
	
	/**
	 * 
	 */
	private transient final Queue<Message> messages;
	
	/**
	 * 
	 */
	private transient final Client client;
	
	/**
	 * 
	 */
	private transient boolean isClose;
	
	/**
	 * 
	 */
	//private transient final Object wait = new Object();
	
	/**
	 * @param client
	 */
	public MessageQueue(final Client client) {
		Objects.requireNonNull(client, "Client");
		this.client = client;
		this.messages = new ConcurrentLinkedQueue<>();
		isClose = false;
	}

	@Override
	public void run() {
		while(!isClose) {
			/*try {
				wait.wait();
			} 
			catch (InterruptedException e) {
				LOGGER.throwing(getClass().getName(), "run", e);
			}*/
			while(!messages.isEmpty()) {
				final Message message = messages.peek();
				try {
					client.getSession().getBasicRemote().sendObject(message.getObject());
					messages.poll().close();
				} 
				catch (IOException|EncodeException e) {
					LOGGER.throwing(getClass().getName(), "run", e);
				}
			}
			try {
				Thread.sleep(40);
			} 
			catch (InterruptedException e) {
				LOGGER.throwing(getClass().getName(), "run", e);
			}
		}
	}

	@Override
	public void close() throws IOException {
		isClose = true;
		//wait.notify();
	}
	
	/**
	 * @param message
	 */
	public void add(final Message message) {
		messages.add(message);
		//wait.notify();
	}
}
