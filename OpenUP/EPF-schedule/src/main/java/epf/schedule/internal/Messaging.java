package epf.schedule.internal;

import java.net.URI;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.websocket.Client;
import epf.util.websocket.MessageQueue;

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
	private transient MessageQueue shellMessages;
	
	/**
	 * 
	 */
	private transient Client shell;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			shell = Client.connectToServer(messagingUrl.resolve("_/schedule/shell"));
			shellMessages = new MessageQueue(shell.getSession());
			executor.submit(shellMessages);
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "postConstruct", ex);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		shellMessages.close();
		try {
			shell.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
		}
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-schedule-messaging");
	}
	
	/**
	 * @return
	 */
	public MessageQueue getMessages() {
		return shellMessages;
	}
}
