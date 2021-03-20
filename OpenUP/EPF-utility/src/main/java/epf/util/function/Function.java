package epf.util.function;

import java.util.concurrent.Callable;
import java.util.stream.Stream;
import epf.util.Var;
import epf.util.lang.Return;
import epf.util.lang.Throw;

/**
 * @author PC
 *
 */
public class Function implements Runnable {
	
	/**
	 * 
	 */
	private transient final Stream<Runnable> stream;
	/**
	 * 
	 */
	private transient Var<Exception> exception;
	/**
	 * 
	 */
	private transient Var<?> _return;
	/**
	 * 
	 */
	private transient Var<Exception> _throw;
	
	/**
	 * @param stream
	 */
	public Function(final Stream<Runnable> stream) {
		this.stream = stream;
	}

	@Override
	public void run() {
		apply(getStream())
		.forEach(runnable -> {
			if(runnable instanceof Callable) {
				final Callable<?> callable = (Callable<?>) runnable;
				try {
					callable.call();
				} 
				catch (Exception e) {
					exception = new Var<Exception>(e);
				}
			}
			else {
				runnable.run();
			}
		});
	}
	
	/**
	 * @param stream
	 * @return
	 */
	protected Stream<Runnable> apply(final Stream<Runnable> stream){
		return stream
				.filter(run -> {
					if(run instanceof Return) {
						_return = (Var<?>) run;
					}
					else if(run instanceof Throw) {
						_throw = (Throw<?>) run;
					}
					return true;
				})
				.takeWhile(run -> getReturn() == null && getThrow() == null && getException() == null)
				.dropWhile(run -> getReturn() != null || getException() != null || getThrow() != null)
				.flatMap(runnable -> {
					if(runnable instanceof Function) {
						final Function func = (Function) runnable;
						return func.apply(func.getStream());
					}
					return Stream.of(runnable);
				});
	}
	
	protected Stream<Runnable> getStream(){
		return stream;
	}
	
	protected Var<Exception> getException(){
		return exception;
	}
	
	/**
	 * @return
	 */
	protected Var<?> getReturn(){
		return _return;
	}
	
	/**
	 * @return
	 */
	protected Var<Exception> getThrow(){
		return _throw;
	}
}
