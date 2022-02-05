package epf.util;

import java.util.concurrent.CompletionStage;

/**
 * @author PC
 *
 */
public interface AutoCloseable {

	/**
	 * @return
	 */
	CompletionStage<Void> close();
}
