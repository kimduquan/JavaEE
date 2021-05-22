/**
 * 
 */
package epf.service.system;

import javax.ws.rs.sse.SseBroadcaster;
import epf.util.logging.Logging;
import java.util.logging.Logger;
import javax.ws.rs.sse.OutboundSseEvent.Builder;

/**
 * @author PC
 *
 */
public class OutputTask implements Runnable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(OutputTask.class.getName());
	
	/**
	 * 
	 */
	private transient final ProcessTask processTask;
	
	/**
     * 
     */
    private transient final SseBroadcaster broadcaster;
    
    /**
     * 
     */
    private transient final Builder builder;
    
    /**
     * 
     */
    private transient final String processId;

	/**
	 * 
	 */
	public OutputTask(final ProcessTask processTask, final SseBroadcaster broadcaster, final Builder builder) {
		this.processTask = processTask;
		this.broadcaster = broadcaster;
		this.builder = builder;
		processId = String.valueOf(processTask.getProcess().pid());
	}

	@Override
	public void run() {
		while(processTask.getProcess().isAlive() || !processTask.getOutput().isEmpty()) {
			final String line = processTask.getOutput().poll();
			broadcaster.broadcast(builder.id(processId).data(line).build());
			try {
				Thread.sleep(40);
			} 
			catch (InterruptedException e) {
				LOGGER.throwing(Thread.class.getName(), "sleep", e);
			}
		}
	}

	/**
	 * 
	 */
	public void close() {
		this.broadcaster.close();
	}
	
	public SseBroadcaster getBroadcaster() {
		return this.broadcaster;
	}
}
