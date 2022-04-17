package epf.messaging.util;

import java.io.Serializable;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;

import epf.messaging.util.reactive.ObjectPublisher;

/**
 * @author PC
 *
 */
public interface PublisherUtil {
	
	/**
	 * @param <T>
	 * @param publisher
	 * @return
	 */
	static <T extends Serializable> PublisherBuilder<T> newPublisher(final ObjectPublisher<T> publisher){
		return ReactiveStreams.fromPublisher(publisher);
	}
}
