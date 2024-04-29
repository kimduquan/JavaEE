package epf.util.concurrent.ext;

import java.util.Objects;
import java.util.concurrent.CompletionStage;
import jakarta.enterprise.event.Event;

/**
 * @param <T>
 */
public class EventEmitter<T> implements Emitter<T> {

	/**
	 *
	 */
	private transient final Event<T> event;
	
	/**
	 * @param event
	 */
	public EventEmitter(final Event<T> event){
		Objects.requireNonNull(event, "Event");
		this.event = event;
	}
	
	/**
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	@Override
	public CompletionStage<T> sendAsync(final T object) throws Exception {
		Objects.requireNonNull(object, "Object");
		return event.fireAsync(object);
	}

	@Override
	public void send(final T object) throws Exception {
		Objects.requireNonNull(object, "Object");
		event.fire(object);
	}
}
