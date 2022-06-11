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
	@Inject
	private transient Event<Object> event;

	@Override
	public void accept(final AsyncEvent<T> t) {
		t.setStage(event.fireAsync(t.getEvent()));
		t.accept();
	}
}
