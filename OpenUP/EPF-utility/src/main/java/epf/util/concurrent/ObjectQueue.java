/**
 * 
 */
package epf.util.concurrent;

import java.io.Closeable;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Logger;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public abstract class ObjectQueue<T extends Object> implements Runnable, Closeable, Consumer<T> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(ObjectQueue.class.getName());
	
	/**
	 * 
	 */
	private transient final Queue<T> objects = new ConcurrentLinkedQueue<>();
	
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
		while(true) {
			if(close.get()) {
				while(!objects.isEmpty()) {
					accept(objects.poll());
				}
				try {
					Thread.sleep(40);
				} 
				catch (InterruptedException e) {
					LOGGER.throwing(getClass().getName(), "run", e);
				}
			}
			else {
				while(!objects.isEmpty()) {
					accept(objects.poll());
				}
				break;
			}
		}
	}

	/**
	 * @param entry
	 */
	public void add(final T entry) {
		objects.add(entry);
	}
}
