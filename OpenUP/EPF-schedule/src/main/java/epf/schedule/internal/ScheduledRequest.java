package epf.schedule.internal;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.Response;
import epf.client.util.RequestBuilder;
import epf.client.util.ResponseUtil;

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
	private transient final RequestBuilder builder;
	
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
	 * @param builder
	 * @param timeOut
	 */
	public ScheduledRequest(UUID uuid, final RequestBuilder builder, final Optional<Duration> timeOut) {
		this.uuid = uuid;
		this.builder = builder;
		this.timeOut = timeOut;
	}

	@Override
	public Response call() throws Exception {
		if(timeOut.isPresent()) {
			response = builder.build().toCompletableFuture().get(timeOut.get().toMillis(), TimeUnit.MILLISECONDS);
		}
		else {
			response = builder.build().toCompletableFuture().get();
		}
		response = ResponseUtil.clone(response);
		response.bufferEntity();
		return response;
	}

	public UUID getUuid() {
		return uuid;
	}

	public RequestBuilder getBuilder() {
		return builder;
	}

	public Optional<Duration> getTimeOut() {
		return timeOut;
	}

	public Response getResponse() {
		return response;
	}
}
