/**
 * 
 */
package epf.util.concurrent;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Logger;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public abstract class ObjectTopic<T extends Object, U extends Consumer<T>> implements Runnable, Closeable, Consumer<T> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(ObjectTopic.class.getName());
	
	/**
	 * 
	 */
	private transient final Queue<T> objects = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Consumer<T>> consumers = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final AtomicBoolean close = new AtomicBoolean(false);

	@Override
	public void close() throws IOException {
		close.set(true);
	}

	@Override
	public void run() {
		while(!close.get()) {
			while(!objects.isEmpty()) {
				final T object = objects.poll();
				consumers.values().parallelStream().forEach(consumer -> {
					consumer.accept(object);
				});
			}
			try {
				Thread.sleep(40);
			} 
			catch (InterruptedException e) {
				LOGGER.throwing(getClass().getName(), "run", e);
			}
		}
	}

	/**
	 * @param entry
	 */
	public void add(final T entry) {
		objects.add(entry);
	}
	
	/**
	 * @param id
	 * @param consumer
	 */
	public void addConsumer(final String id, final Consumer<T> consumer) {
		consumers.put(id, consumer);
	}
	
	/**
	 * @param id
	 */
	public void removeConsumer(final String id) {
		consumers.remove(id);
	}
}
