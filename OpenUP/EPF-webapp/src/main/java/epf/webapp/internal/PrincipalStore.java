package epf.webapp.internal;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import epf.webapp.messaging.TextMessage;
import epf.webapp.naming.Naming;
import epf.webapp.security.TokenPrincipal;
import epf.webapp.security.auth.IDTokenPrincipal;
import epf.webapp.security.util.JwtUtil;

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
	public void putPrincipal(@Observes final IDTokenPrincipal principal) throws Exception {
		final Set<String> otherUsers = principals.keySet();
		principals.put(principal.getName(), JwtUtil.decode(principal.getId_token()).getClaimsMap());
		send(principal.getName(), otherUsers);
	}
	
	/**
	 * @param principal
	 * @throws Exception
	 */
	public void putPrincipal(@Observes final TokenPrincipal principal) throws Exception {
		final Set<String> otherUsers = principals.keySet();
		principals.put(principal.getName(), JwtUtil.decode(principal.getRememberToken().orElse(principal.getRawToken())).getClaimsMap());
		send(principal.getName(), otherUsers);
	}
}
