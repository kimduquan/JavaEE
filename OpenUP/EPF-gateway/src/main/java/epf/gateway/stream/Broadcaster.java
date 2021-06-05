/**
 * 
 */
package epf.gateway.stream;

import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseBroadcaster;

/**
 * @author PC
 *
 */
public class Broadcaster implements AutoCloseable {
	
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
		sse.broadcast(builder.data(message).build());
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
