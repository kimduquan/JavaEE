package epf.util.lang;

import java.util.function.Supplier;
import java.util.stream.Stream;
import epf.util.function.PredicateFunction;

/**
 * @author PC
 *
 */
public class While extends PredicateFunction {
	
	/**
	 * @param predicate
	 */
	public While(final Supplier<Boolean> predicate) {
		super(predicate, Stream.of());
	}
	
	/**
	 * @param predicate
	 * @param stream
	 */
	public While(final Supplier<Boolean> predicate, final Stream<Runnable> stream) {
		super(predicate, stream);
	}
}
