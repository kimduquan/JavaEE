package epf.webapp.messaging;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import epf.webapp.internal.TokenPrincipal;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class PrincipalStore {

	/**
	 * 
	 */
	private transient final Map<String, Map<String, Object>> principals = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject @Push(channel = Naming.Messaging.SESSION)
	private transient PushContext context;
	
	/**
	 * @param user
	 * @param otherUsers
	 */
	private void send(final String user, final Set<String> otherUsers) {
		final TextMessage message = new TextMessage();
		message.setDeliveryTime(Instant.now().toEpochMilli());
		message.setReplyTo(user);
		message.setText("");
		context.send(message, otherUsers);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Map<String, Object> getClaims(final String name) {
		return principals.get(name);
	}
	
	/**
	 * @param principal
	 * @throws Exception
	 */
	public void putPrincipal(@Observes final TokenPrincipal principal) throws Exception {
		final Set<String> otherUsers = principals.keySet();
		principals.put(principal.getName(), principal.getClaims());
		send(principal.getName(), otherUsers);
	}
}
