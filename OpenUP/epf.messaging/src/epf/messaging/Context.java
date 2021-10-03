package epf.messaging;

import java.io.Closeable;

/**
 * @author PC
 *
 */
public interface Context extends Closeable {

	/**
	 * @return
	 */
	MessageProducer createProducer();
	
	/**
	 * @param consumer
	 */
	void createConsumer(final MessageConsumer consumer);
}
