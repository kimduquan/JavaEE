/**
 * 
 */
package epf.util.concurrent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author PC
 *
 */
public class ObjectTopic<T extends Object, U extends Consumer<T>> extends ObjectQueue<T> {
	
	/**
	 * 
	 */
	private transient final Map<String, Consumer<T>> consumers = new ConcurrentHashMap<>();
	
	/**
	 * @param id
	 * @param consumer
	 */
	public void addConsumer(final String id, final Consumer<T> consumer) {
		Objects.requireNonNull(id, "String");
		Objects.requireNonNull(consumer, "Consumer");
		consumers.put(id, consumer);
	}
	
	/**
	 * @param id
	 */
	public void removeConsumer(final String id) {
		Objects.requireNonNull(id, "String");
		consumers.remove(id);
	}

	@Override
	public void accept(final T object) {
		consumers.values().parallelStream().forEach(consumer -> {
			consumer.accept(object);
		});
	}
}
