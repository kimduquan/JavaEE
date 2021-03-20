package epf.util.lang;

import java.util.stream.Stream;
import epf.util.function.Function;

/**
 * @author PC
 *
 */
public class Do extends Function {
	
	/**
	 * @param stream
	 */
	public Do(final Stream<Runnable> stream) {
		super(stream);
	}
	
	@Override
	public Stream<Runnable> apply(final Stream<Runnable> stream){
		return super
				.apply(stream)
				.takeWhile(func -> {
					if(func instanceof While) {
						final While _while = (While) func;
						if(_while.getPredicate().get() == null) {
							return true;
						}
						return _while.getPredicate().get();
					}
					else if(func instanceof Break) {
						return false;
					}
					return true;
					}
				)
				.flatMap(func -> { 
					if(func instanceof Continue) {
						return Stream.of();
					}
					return Stream.of(func); 
					}
				);
	}
}
