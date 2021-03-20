package epf.util.lang;

import java.util.function.Supplier;
import java.util.stream.Stream;
import epf.util.function.PredicateFunction;

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
