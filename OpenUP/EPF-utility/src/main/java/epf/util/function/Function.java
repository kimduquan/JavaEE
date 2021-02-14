package epf.util.function;

import java.util.concurrent.Callable;
import java.util.stream.Stream;
import epf.util.Var;
import epf.util.lang.Return;

public class Function implements Runnable {
	
	private Stream<Runnable> stream;
	private Var<Exception> exception;
	private Var<?> _return;
	
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
				.takeWhile(line -> !(line instanceof Return) && exception() == null && _return() == null)
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
}
