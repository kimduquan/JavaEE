package epf.query.internal.messaging;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.messaging.client.Client;
import epf.messaging.client.MessageEncoder;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class Messaging implements HealthCheck {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Messaging.class.getName());

	/**
	 *
	 */
	private transient final MessageEncoder encoder = new MessageEncoder();
	
	/**
	 *
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			client = epf.messaging.client.Messaging.connectToServer(messagingUrl, Optional.empty(), Naming.QUERY, Optional.empty());
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[Messaging.postConstruct]", e);
		}
	}

	@Override
	public HealthCheckResponse call() {
		if(client == null || !client.getSession().isOpen()) {
			return HealthCheckResponse.down("EPF-query-messaging");
		}
		return HealthCheckResponse.up("EPF-query-messaging");
	}

	/**
	 * @param event
	 */
	public void accept(final EntityEvent event) {
		try {
			final String encodedEvent = encoder.encode(event);
			client.getSession().getAsyncRemote().sendText(encodedEvent);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[Messaging.accept]", ex);
		}
	}
}
