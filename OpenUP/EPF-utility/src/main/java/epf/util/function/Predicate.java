package epf.util.function;

import java.util.function.Supplier;
import epf.util.Var;

public class Predicate extends Var<Boolean> implements Runnable {
	
	private Supplier<Boolean> predicate;
	
	public Predicate(Supplier<Boolean> predicate) {
		this.predicate = predicate;
	}

	@Override
	public void run() {
		set(predicate.get());
	}

}
