package epf.util.lang;

import java.util.function.Supplier;
import java.util.stream.Stream;
import epf.util.function.PredicateFunction;

public class While extends PredicateFunction {
	
	public While(Supplier<Boolean> predicate) {
		super(predicate, Stream.of());
	}
	
	public While(Supplier<Boolean> predicate, Stream<Runnable> stream) {
		super(predicate, stream);
	}
}
