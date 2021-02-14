package epf.util.lang;

import java.util.concurrent.Callable;
import java.util.function.Supplier;
import epf.util.function.Predicate;

public class Assert implements Runnable, Callable<Void> {

	private Predicate predicate;
	
	public Assert(Supplier<Boolean> func) {
		predicate = new Predicate(func);
	}

	@Override
	public void run() {
		predicate.run();
	}

	@Override
	public Void call() throws Exception {
		predicate.run();
		if(!predicate.get()) {
			throw new AssertionException();
		}
		return null;
	}
}
