package epf.function.internal;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import epf.util.Var;

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
	private transient Var<?> returnVar;
	/**
	 * 
	 */
	private transient Var<Exception> throwVar;
	
	/**
	 * @param stream
	 */
	public Function(final Stream<Runnable> stream) {
		Objects.requireNonNull(stream);
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
						returnVar = (Var<?>) run;
					}
					else if(run instanceof Throw) {
						throwVar = (Throw<?>) run;
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
		return returnVar;
	}
	
	/**
	 * @return
	 */
	protected Var<Exception> getThrow(){
		return throwVar;
	}
}
