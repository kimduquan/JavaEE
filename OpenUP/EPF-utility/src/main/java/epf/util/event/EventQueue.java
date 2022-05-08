package epf.util.event;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import epf.util.concurrent.ObjectQueue;

/**
 * @param <T>
 */
@Dependent
public class EventQueue<T extends Object> extends ObjectQueue<AsyncEvent<T>> {
	
	/**
	 *
	 */
	private transient final EventEmitter<T> emitter;
	
	/**
	 *
	 */
	@Inject
	private transient Event<Object> event;
	
	/**
	 * 
	 */
	public EventQueue() {
		emitter = new EventEmitter<>(this);
	}

	@Override
	public void accept(final AsyncEvent<T> t) {
		t.setStage(event.fireAsync(t.getEvent()));
		t.accept();
	}

	public EventEmitter<T> getEmitter() {
		return emitter;
	}
}
