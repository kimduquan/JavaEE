package epf.webapp.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Messaging {
	
	/**
	 * 
	 */
	@Inject @Push(channel = Naming.Messaging.MESSAGING)
	private transient PushContext context;

	/**
	 * @param message
	 */
	public void sendMessage(final Message message) {
		context.send(message);
	}
}
