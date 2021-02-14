package epf.util.lang;

import java.util.function.Supplier;
import java.util.stream.Stream;
import epf.util.function.PredicateFunction;

public class If extends PredicateFunction {

	public If(Supplier<Boolean> predicate, Stream<Runnable> stream) {
		super(predicate, stream);
	}
}
