package epf.function;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author PC
 *
 */
public class If extends PredicateFunction {

	/**
	 * @param predicate
	 * @param stream
	 */
	public If(final Supplier<Boolean> predicate, final Stream<Runnable> stream) {
		super(predicate, stream);
	}
}
