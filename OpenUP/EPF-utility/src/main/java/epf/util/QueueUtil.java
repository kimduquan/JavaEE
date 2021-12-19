package epf.util;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author PC
 *
 */
public interface QueueUtil {

	/**
	 * @param <E>
	 * @param queue
	 * @param action
	 */
	static <E> void ifEmpty(final Queue<E> queue, final Supplier<? extends E> action){
		Objects.requireNonNull(queue, "Queue");
		Objects.requireNonNull(action, "Supplier");
		synchronized(queue) {
			if(queue.isEmpty()) {
				final E element = action.get();
				queue.add(element);
			}
		}
	}
	
	/**
	 * @param <E>
	 * @param queue
	 * @param action
	 */
	static <E, R> Optional<R> peek(final Queue<E> queue, final Function<? super E, R> function) {
		Objects.requireNonNull(queue, "Queue");
		Objects.requireNonNull(function, "Function");
		final E element = queue.poll();
		Optional<R> result = Optional.empty();
		if(element != null) {
			try {
				result = Optional.ofNullable(function.apply(element));
			}
			finally {
				queue.add(element);
			}
		}
		return result;
	}
}
