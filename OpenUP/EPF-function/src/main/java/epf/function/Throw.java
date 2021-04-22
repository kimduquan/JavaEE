package epf.function;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

import epf.util.Var;

/**
 * @author PC
 *
 * @param <T>
 */
public class Throw<T extends Exception> extends Var<Exception> implements Callable<Void>, Runnable {
	
	/**
	 * 
	 */
	private transient final Class<?> cls;
	
	/**
	 * @param cls
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws Exception
	 */
	public Throw(final Class<T> cls) {
		super();
		this.cls = cls;
	}

	@Override
	public Void call() throws Exception {
		@SuppressWarnings("unchecked")
		final Constructor<T> constructor = (Constructor<T>) cls.getConstructor();
		set(constructor.newInstance());
		throw get();
	}

	@Override
	public void run() {
		set(get());
	}

}
