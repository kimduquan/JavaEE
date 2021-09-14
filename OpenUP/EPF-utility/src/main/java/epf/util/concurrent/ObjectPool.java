/**
 * 
 */
package epf.util.concurrent;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * @author PC
 *
 */
public class ObjectPool<T extends Object, U extends Consumer<T>> extends ObjectQueue<T> {

	/**
	 * 
	 */
	private transient final Queue<Consumer<T>> consumers = new ConcurrentLinkedQueue<>();
	
	/**
	 * @param consumer
	 */
	public void addConsumer(final Consumer<T> consumer) {
		Objects.requireNonNull(consumer, "Consumer");
		consumers.add(consumer);
	}
	
	@Override
	public void accept(final T t) {
		final Consumer<T> consumer = consumers.poll();
		if(consumer != null) {
			consumer.accept(t);
			consumers.add(consumer);
		}
	}
}
