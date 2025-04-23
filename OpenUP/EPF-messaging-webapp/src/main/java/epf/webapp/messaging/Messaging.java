package epf.webapp.messaging;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import epf.webapp.naming.Naming;

/**
 * 
 */
@ApplicationScoped
public class Messaging {
	
	/**
	 * 
	 */
	private List<String> sessions = new CopyOnWriteArrayList<>();

	/**
	 * 
	 */
	@Inject @Push(channel = Naming.Messaging.APPLICATION)
	private PushContext application;
	
	
	/**
	 * @param session
	 */
	public void onOpen(final String session) {
		sessions.add(session);
	}
	
	/**
	 * @param session
	 */
	public void onClose(final String session) {
		sessions.remove(session);
	}
	
	/**
	 * @param message
	 * @throws Exception
	 */
	public void send(final Object message) {
		application.send(message, sessions);
	}
}
