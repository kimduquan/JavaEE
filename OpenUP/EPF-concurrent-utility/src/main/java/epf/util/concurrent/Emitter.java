package epf.util.concurrent;

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
	CompletionStage<T> sendAsync(final T object) throws Exception;
	
	/**
	 * @param object
	 * @throws Exception
	 */
	void send(final T object) throws Exception;
}
