package epf.util.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import epf.util.concurrent.ObjectQueue;

/**
 * @param <T>
 */
public class EventEmitter<T> implements Emitter<T> {

	/**
	 *
	 */
	private transient final ObjectQueue<AsyncEvent<T>> eventQueue;
	
	/**
	 * @param eventQueue
	 */
	public EventEmitter(final ObjectQueue<AsyncEvent<T>> eventQueue){
		this.eventQueue = eventQueue;
	}
	
	/**
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	@Override
	public CompletionStage<T> send(final T object) throws Exception {
		if(eventQueue != null) {
			final AsyncEvent<T> asyncEvent = new AsyncEvent<>(object);
			eventQueue.add(asyncEvent);
			asyncEvent.waitAccept();
			return asyncEvent.getStage();
		}
		return CompletableFuture.completedFuture(object);
	}
}
