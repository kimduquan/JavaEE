package epf.util.lang;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

public class Throw<T extends Exception> implements Callable<Void>, Runnable {
	
	private Constructor<T> constructor;
	
	public Throw(Class<T> cls) throws Exception{
		this.constructor = cls.getConstructor();
	}

	@Override
	public Void call() throws Exception {
		throw constructor.newInstance();
	}

	@Override
	public void run() {
	}

}
