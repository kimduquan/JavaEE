package epf.util.event;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 */
public class AsyncEvent<T> {

	/**
	 *
	 */
	private transient final T event;
	/**
	 *
	 */
	private transient CompletionStage<T> stage;
	/**
	 *
	 */
	private transient final AtomicBoolean wait;
	
	/**
	 * @param event
	 */
	public AsyncEvent(final T event) {
		this.event = event;
		wait = new AtomicBoolean(true);
	}

	public CompletionStage<T> getStage() {
		return stage;
	}

	public void setStage(final CompletionStage<T> stage) {
		this.stage = stage;
	}

	public T getEvent() {
		return event;
	}
	
	/**
	 * 
	 */
	public void accept() {
		wait.set(false);
	}
	
	/**
	 * @throws Exception
	 */
	public void waitAccept() throws Exception {
		while(wait.get()) {
			Thread.sleep(1);
		}
	}
}
