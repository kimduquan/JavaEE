package epf.function.internal;

import java.util.stream.Stream;

/**
 * @author PC
 *
 */
public class DoBlock extends Function {
	
	/**
	 * @param stream
	 */
	public DoBlock(final Stream<Runnable> stream) {
		super(stream);
	}
	
	@Override
	public Stream<Runnable> apply(final Stream<Runnable> stream){
		return super
				.apply(stream)
				.takeWhile(func -> {
					if(func instanceof While) {
						final While whileFunc = (While) func;
						return whileFunc.getPredicate().get().orElse(true);
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
