package epf.util.function;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author PC
 *
 */
public class PredicateFunction extends Function {
	
	/**
	 * 
	 */
	private transient final Predicate predicate;

	/**
	 * @param predicate
	 * @param stream
	 */
	public PredicateFunction(final Supplier<Boolean> predicate, final Stream<Runnable> stream) {
		super(stream);
		Objects.requireNonNull(predicate);
		this.predicate = new Predicate(predicate);
	}
	
	@Override
	public void run() {
		predicate.run();
		if(predicate.get()) {
			super.run();
		}
	}
	
	public Predicate getPredicate() {
		return this.predicate;
	}
}
