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
		return HealthCheckResponse.up("EPF-query-messaging");
	}

	/**
	 * @param event
	 * @throws Exception 
	 */
	public void accept(final EntityEvent event) throws Exception {
		client.getSession().getAsyncRemote().sendText(encoder.encode(event));
	}
}
