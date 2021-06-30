/**
 * 
 */
package epf.gateway.stream;

import java.util.logging.Logger;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseBroadcaster;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class Broadcaster implements AutoCloseable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Broadcaster.class.getName());
	
	/**
	 * 
	 */
	private transient final OutboundSseEvent.Builder builder;
	
	/**
	 * 
	 */
	private transient final SseBroadcaster sse;

	/**
	 * 
	 */
	public Broadcaster(final OutboundSseEvent.Builder builder, final SseBroadcaster broadcaster) {
		this.builder = builder;
		this.sse = broadcaster;
	}
	
	/**
	 * @param message
	 */
	public void broadcast(final String message) {
		try {
			sse.broadcast(builder.data(message).build());
		}
		catch(Exception ex) {
			LOGGER.throwing(getClass().getName(), "broadcast", ex);
		}
	}

	@Override
	public void close() throws Exception {
		sse.close();
	}
	
	/**
	 * @return
	 */
	public SseBroadcaster getBroadcaster() {
		return sse;
	}
}
