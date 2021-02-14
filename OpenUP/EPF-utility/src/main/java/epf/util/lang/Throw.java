package epf.util.lang;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

import epf.util.Var;

public class Throw<T extends Exception> extends Var<Exception> implements Callable<Void>, Runnable {
	
	private Constructor<T> constructor;
	
	public Throw(Class<T> cls) throws Exception{
		this.constructor = cls.getConstructor();
	}

	@Override
	public Void call() throws Exception {
		set(constructor.newInstance());
		throw get();
	}

	@Override
	public void run() {
	}

}
