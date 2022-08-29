package epf.logging;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
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
	 * @param name
	 * @param url
	 * @throws Exception
	 */
	protected void newLogger(final String name, final URI url) throws Exception {
		final Client client = Client.connectToServer(url);
		final Logger logger = new Logger(name, client);
		loggers.put(name, logger);
	}
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			newLogger("EPF-query", messagingUrl.resolve("_/query/_"));
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "[LogManager.loggers]", ex);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		loggers.values().stream().forEach(logger -> {
			try {
				logger.close();
			} 
			catch (Exception e) {
				LOGGER.log(Level.WARNING, "[Logger.close]", e);
			}
		});
		loggers.clear();
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-logging");
	}

}
