package epf.util.function;

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
	private transient final Supplier<Boolean> predicate;
	
	/**
	 * @param predicate
	 */
	public Predicate(final Supplier<Boolean> predicate) {
		super();
		this.predicate = predicate;
	}

	@Override
	public void run() {
		set(predicate.get());
	}

}
