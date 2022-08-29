package epf.logging;

import java.util.Objects;
import java.util.logging.Level;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
public class Logger implements AutoCloseable {
	
	/**
	 * 
	 */
	private transient final java.util.logging.Logger logger;
	
	/**
	 * 
	 */
	private transient final Client client;
	
	/**
	 * @param name
	 * @param client
	 */
	public Logger(final String name, final Client client) {
		Objects.requireNonNull(name, "String");
		Objects.requireNonNull(client, "Client");
		logger = epf.util.logging.LogManager.getLogger(name);
		this.client = client;
		this.client.onMessage((message, session) -> {
			logger.log(Level.INFO, message);
		});
	}
	
	@Override
	public void close() throws Exception {
		client.close();
	}
}
