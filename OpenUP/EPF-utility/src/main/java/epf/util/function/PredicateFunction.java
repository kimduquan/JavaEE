package epf.util.function;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class PredicateFunction extends Function {
	
	private Predicate predicate;

	public PredicateFunction(Supplier<Boolean> predicate, Stream<Runnable> stream) {
		super(stream);
		this.predicate = new Predicate(predicate);
	}
	
	@Override
	public void run() {
		predicate.run();
		if(predicate.get()) {
			super.run();
		}
	}
	
	public Predicate predicate() {
		return this.predicate;
	}
}
