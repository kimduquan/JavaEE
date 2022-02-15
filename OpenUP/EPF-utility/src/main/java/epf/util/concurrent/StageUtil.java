package epf.util.concurrent;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * @author PC
 *
 */
public interface StageUtil {

	/**
	 * @param <C>
	 * @param <V>
	 * @param stage
	 * @param function
	 * @return
	 */
	static <C extends epf.util.AutoCloseable, V> CompletionStage<V> stage(
			final CompletionStage<C> stage,
			final Function<C, ? extends CompletionStage<V>> function) {
		return stage.thenCompose(c -> function.apply(c).whenComplete((v, err) -> { c.close(); }));
	}
}
