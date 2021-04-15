/**
 * 
 */
package epf.client.messaging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.Session;

/**
 * @author PC
 *
 */
@ClientEndpoint(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class Client implements AutoCloseable {

	/**
	 * 
	 */
	private transient Session session;

	/**
	 * 
	 */
	private transient Consumer<? super Object> messageConsumer;
	
	/**
	 * 
	 */
	private transient final Queue<Object> messages;
	
	/**
	 * 
	 */
	protected Client() {
		super();
		messages = new ConcurrentLinkedQueue<>();
	}
	
	public Session getSession() {
		return session;
	}
	
	protected void setSession(final Session session) {
		this.session = session;
	}
	
	public Queue<Object> getMessages() {
		return messages;
	}

	@Override
	public void close() throws Exception {
		session.close();
		messages.clear();
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final Object message, final Session session) {
		if(messageConsumer == null) {
			messages.add(message);
		}
		else {
			messageConsumer.accept(message);
		}
	}
	
	/**
	 * @param messageConsumer
	 */
	public void onMessage(final Consumer<? super Object> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
}
