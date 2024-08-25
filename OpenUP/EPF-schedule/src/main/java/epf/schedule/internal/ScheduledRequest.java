package epf.schedule.internal;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import javax.ws.rs.core.Response;

/**
 * @author PC
 *
 */
public class ScheduledRequest implements Callable<Response> {
	
	/**
	 * 
	 */
	private final UUID uuid;
	
	/**
	 * 
	 */
	private transient final Optional<Duration> timeOut;
	
	/**
	 * 
	 */
	private transient Response response;
	
	/**
	 * @param uuid
	 * @param timeOut
	 */
	public ScheduledRequest(UUID uuid, final Optional<Duration> timeOut) {
		this.uuid = uuid;
		this.timeOut = timeOut;
	}

	@Override
	public Response call() throws Exception {
		response.bufferEntity();
		return response;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Optional<Duration> getTimeOut() {
		return timeOut;
	}

	public Response getResponse() {
		return response;
	}
}
