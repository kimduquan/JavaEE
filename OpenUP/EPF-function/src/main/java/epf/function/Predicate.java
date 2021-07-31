package epf.function;

import java.util.Objects;
import java.util.function.Supplier;
import epf.util.Var;

/**
 * @author PC
 *
 */
public class Predicate extends Var<Boolean> implements Runnable {
	
	/**
	 * 
	 */
	private transient final Supplier<Boolean> predic;
	
	/**
	 * @param predicate
	 */
	public Predicate(final Supplier<Boolean> predicate) {
		super();
		Objects.requireNonNull(predicate);
		this.predic = predicate;
	}

	@Override
	public void run() {
		set(predic.get());
	}

}
