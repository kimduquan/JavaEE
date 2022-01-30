/**
 * 
 */
package epf.logging;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class LogManager implements HealthCheck {
	
	/**
	 * 
	 */
	private static transient final java.util.logging.Logger LOGGER = epf.util.logging.LogManager.getLogger(LogManager.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, Logger> loggers = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * @param name
	 * @param url
	 * @throws DeploymentException
	 * @throws IOException
	 */
	protected void newLogger(final String name, final URI url) throws DeploymentException, IOException {
		final Client client = Client.connectToServer(url);
		final Logger logger = new Logger(name, client);
		executor.submit(logger);
		loggers.put(name, logger);
		client.onMessage(message -> {
			logger.add(message);
		});
	}
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			newLogger("EPF-persistence", messagingUrl.resolve(Naming.PERSISTENCE));
			newLogger("EPF-security", messagingUrl.resolve(Naming.SECURITY));
			newLogger("EPF-cache", messagingUrl.resolve(Naming.CACHE));
			newLogger("EPF-shell-schedule", messagingUrl.resolve("schedule/shell"));
			newLogger("EPF-persistence-load", messagingUrl.resolve("persistence/post-load"));
			newLogger("EPF-file", messagingUrl.resolve(Naming.FILE));
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
		loggers.values().parallelStream().forEach(Logger::close);
		loggers.clear();
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-logging");
	}

}
