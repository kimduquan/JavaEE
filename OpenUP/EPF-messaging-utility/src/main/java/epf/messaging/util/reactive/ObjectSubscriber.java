package epf.messaging.util.reactive;

import java.io.Serializable;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * @author PC
 *
 */
public class ObjectSubscriber<T extends Serializable> implements Subscriber<T> {
	
	/**
	 * 
	 */
	private transient final org.reactivestreams.Subscriber<? super T> subscriber;
	
	/**
	 * @param subscriber
	 */
	public ObjectSubscriber(final org.reactivestreams.Subscriber<? super T> subscriber){
		this.subscriber = subscriber;
	}

	@Override
	public void onComplete() {
		subscriber.onComplete();
	}

	@Override
	public void onError(final Throwable error) {
		this.subscriber.onError(error);
	}

	@Override
	public void onNext(final T object) {
		this.subscriber.onNext(object);
	}

	@Override
	public void onSubscribe(final Subscription subscription) {
		this.subscriber.onSubscribe(new ObjectSubscription(subscription));
	}
}
