package epf.util.function;

import java.util.concurrent.Callable;
import java.util.stream.Stream;
import epf.util.Var;
import epf.util.lang.Return;
import epf.util.lang.Throw;

public class Function implements Runnable {
	
	private Stream<Runnable> stream;
	private Var<Exception> exception;
	private Var<?> _return;
	private Var<Exception> _throw;
	
	public Function(Stream<Runnable> stream) {
		this.stream = stream;
	}

	@Override
	public void run() {
		apply(stream())
		.forEach(runnable -> {
			if(runnable instanceof Callable) {
				Callable<?> callable = (Callable<?>) runnable;
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
	
	protected Stream<Runnable> apply(Stream<Runnable> stream){
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
				.takeWhile(run -> !(run instanceof Return) && !(run instanceof Throw) && exception() == null)
				.dropWhile(run -> _return() != null || exception() != null || _throw() != null)
				.flatMap(runnable -> {
					if(runnable instanceof Function) {
						Function func = (Function) runnable;
						return func.apply(func.stream());
					}
					return Stream.of(runnable);
				});
	}
	
	protected Stream<Runnable> stream(){
		return stream;
	}
	
	protected Var<Exception> exception(){
		return exception;
	}
	
	protected Var<?> _return(){
		return _return;
	}
	
	protected Var<Exception> _throw(){
		return _throw;
	}
}
