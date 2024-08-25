package epf.shell.messaging;

import java.io.PrintWriter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import epf.shell.SYSTEM;

/**
 * 
 */
@ClientEndpoint
@ApplicationScoped
public class MessagingClient {

	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.ERR)
	transient PrintWriter err;
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final String message, final Session session) {
		out.println(message);
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(final Session session, final Throwable throwable) {
		throwable.printStackTrace(err);
	}
}
