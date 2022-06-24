package epf.function.internal;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author PC
 *
 */
public class IfBlock extends PredicateFunction {

	/**
	 * @param predicate
	 * @param stream
	 */
	public IfBlock(final Supplier<Boolean> predicate, final Stream<Runnable> stream) {
		super(predicate, stream);
	}
}
