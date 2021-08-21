/**
 * 
 */
package epf.persistence.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Logger;
import epf.client.messaging.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class MessageTask implements Runnable, Closeable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(MessageTask.class.getName());
	
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
	 * @param messages
	 * @param client
	 */
	public MessageTask(final Client client, final Queue<Message> messages) {
		Objects.requireNonNull(client, "Client");
		Objects.requireNonNull(messages, "Queue");
		this.client = client;
		this.messages = messages;
		isClose = false;
	}

	@Override
	public void run() {
		while(!isClose) {
			while(!messages.isEmpty()) {
				final Message message = messages.peek();
				try {
					client.getSession().getBasicRemote().sendObject(message.getObject());
					messages.poll().close();
				} 
				catch (Exception e) {
					LOGGER.throwing(getClass().getName(), "run", e);
				}
			}
			try {
				Thread.sleep(40);
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "run", e);
			}
		}
	}

	@Override
	public void close() throws IOException {
		isClose = true;
	}
}
