/**
 * 
 */
package epf.logging;

import java.util.Objects;
import epf.util.concurrent.ObjectQueue;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
public class Logger extends ObjectQueue<String> {
	
	/**
	 * 
	 */
	private static transient final java.util.logging.Logger LOGGER = epf.util.logging.Logging.getLogger(Logger.class.getName());
	
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
	 */
	public Logger(final String name, final Client client) {
		Objects.requireNonNull(name, "String");
		Objects.requireNonNull(client, "Client");
		logger = epf.util.logging.Logging.getLogger(name);
		this.client = client;
	}

	@Override
	public void accept(final String log) {
		logger.info(log);
	}
	
	@Override
	public void close() {
		try {
			client.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "close", e);
		}
		super.close();
	}
}
