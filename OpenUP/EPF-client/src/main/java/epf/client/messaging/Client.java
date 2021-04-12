/**
 * 
 */
package epf.client.messaging;

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
	private transient Consumer<Object> messageConsumer;
	
	/**
	 * 
	 */
	protected Client() {
		super();
	}
	
	public Session getSession() {
		return session;
	}
	
	protected void setSession(final Session session) {
		this.session = session;
	}

	@Override
	public void close() throws Exception {
		session.close();
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final Object message, final Session session) {
		if(messageConsumer != null) {
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
