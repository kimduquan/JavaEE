package epf.shell.messaging;

import java.io.PrintWriter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
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
		out.println(String.format("[%s]%s", session.getId(), message));
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(final Session session, final Throwable throwable) {
		err.println(String.format("[%s]", session.getId()));
		throwable.printStackTrace(err);
	}
}
