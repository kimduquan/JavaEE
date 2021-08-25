/**
 * 
 */
package epf.util.concurrent;

import java.io.Closeable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author PC
 *
 */
public abstract class ObjectQueue<T extends Object> implements Runnable, Closeable, Consumer<T> {
	
	/**
	 * 
	 */
	private transient final Queue<T> objects = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	private transient final AtomicBoolean close = new AtomicBoolean(false);

	@Override
	public void close() {
		close.set(true);
	}

	@Override
	public void run() {
		while(true) {
			if(close.get()) {
				while(!objects.isEmpty()) {
					accept(objects.poll());
				}
				break;
			}
			else {
				while(!objects.isEmpty()) {
					accept(objects.poll());
				}
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
