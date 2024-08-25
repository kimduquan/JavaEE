package epf.shell.stream;

import java.io.PrintWriter;
import epf.shell.SYSTEM;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;

/**
 * 
 */
@RequestScoped
public class StreamClient {
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;

	@OnMessage
	public void onMessage(final Session session, final String message) {
		out.print(message);
	}
}
