package epf.util.lang;

import java.util.stream.Stream;
import epf.util.function.Function;

public class Do extends Function {
	
	public Do(Stream<Runnable> stream) {
		super(stream);
	}
	
	@Override
	public Stream<Runnable> apply(Stream<Runnable> stream){
		return super
				.apply(stream)
				.takeWhile(func -> {
					if(func instanceof While) {
						While _while = ((While) func);
						if(_while.predicate().get() == null) {
							return true;
						}
						return _while.predicate().get();
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
