package epf.util.io;

import java.util.concurrent.CompletionStage;

/**
 * @author PC
 *
 */
public interface Closeable {

	/**
	 * @return
	 */
	CompletionStage<Void> close();
}
