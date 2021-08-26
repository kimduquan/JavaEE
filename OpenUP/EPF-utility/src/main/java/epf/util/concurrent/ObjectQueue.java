/**
 * 
 */
package epf.util.concurrent;

import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
	private static transient final Logger LOGGER = Logging.getLogger(ObjectQueue.class.getName());
	
	/**
	 * 
	 */
	private transient final Object BREAK = new Object();
	
	/**
	 * 
	 */
	private transient final BlockingQueue<Object> objects = new LinkedBlockingQueue<>();
	
	/**
	 * 
	 */
	private transient final AtomicBoolean close = new AtomicBoolean(false);

	@Override
	public void close() {
		close.set(true);
		objects.add(BREAK);
	}

	@Override
	public void run() {
		while(!close.get()) {
			try {
				final Object object = objects.take();
				if(BREAK == object) {
					break;
				}
				@SuppressWarnings("unchecked")
				final T obj = (T) object;
				accept(obj);
			} 
			catch (InterruptedException e) {
				LOGGER.throwing(getClass().getName(), "run", null);
			}
		}
	}

	/**
	 * @param entry
	 */
	public void add(final T entry) {
		Objects.requireNonNull(entry, "T");
		objects.add(entry);
	}
}
