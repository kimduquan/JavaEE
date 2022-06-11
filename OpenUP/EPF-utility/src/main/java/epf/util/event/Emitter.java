package epf.util.event;

import java.util.concurrent.CompletionStage;

/**
 * 
 */
public interface Emitter<T> {

	/**
	 * @param object
	 * @return
	 * @throws Exception
	 */
	CompletionStage<T> send(final T object) throws Exception;
}
