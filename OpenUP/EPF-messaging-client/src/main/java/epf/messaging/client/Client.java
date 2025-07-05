package epf.messaging.client;

import java.util.function.Consumer;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;

@ClientEndpoint(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class Client implements AutoCloseable {

	private transient Session session;

	private transient Consumer<? super Object> messageConsumer;
	
	private transient Consumer<? super Throwable> errorConsumer;
	
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
	
	@OnMessage
    public void onMessage(final Object message, final Session session) {
		if(messageConsumer != null) {
			messageConsumer.accept(message);
		}
	}
	
	@OnError
	public void onError(final Session session, final Throwable throwable) {
		if(errorConsumer != null) {
			errorConsumer.accept(throwable);
		}
	}
	
	public void onError(final Consumer<? super Throwable> errorConsumer) {
		this.errorConsumer = errorConsumer;
	}
	
	public void onMessage(final Consumer<? super Object> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
}
