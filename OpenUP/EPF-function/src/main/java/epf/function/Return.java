package epf.function;

import epf.util.Var;

/**
 * @author PC
 *
 * @param <R>
 */
public class Return<R> extends Var<R> implements Runnable {

	@Override
	public void run() {
		set(get());
	}
}
