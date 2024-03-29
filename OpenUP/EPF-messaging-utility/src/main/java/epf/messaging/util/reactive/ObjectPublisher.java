/**
 * 
 */
package epf.messaging.util.reactive;

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 * @author PC
 *
 */
public class ObjectPublisher<T extends Serializable> implements Publisher<T>, AutoCloseable {
	
	/**
	 * 
	 */
	private final transient SubmissionPublisher<T> publisher;
	
	/**
	 * @param executor
	 */
	public ObjectPublisher(final Executor executor) {
		publisher = new SubmissionPublisher<T>(executor, Flow.defaultBufferSize());
	}

	@Override
	public void subscribe(final Subscriber<? super T> subscriber) {
		publisher.subscribe(new ObjectSubscriber<T>(subscriber));
	}

	@Override
	public void close() throws Exception {
		publisher.close();
	}
	
	/**
	 * @param object
	 */
	public void submit(final T object) {
		publisher.submit(object);
	}
}
