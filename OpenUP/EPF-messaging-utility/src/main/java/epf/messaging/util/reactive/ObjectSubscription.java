package epf.messaging.util.reactive;

import org.reactivestreams.Subscription;

/**
 * @author PC
 *
 */
public class ObjectSubscription implements Subscription {
	
	/**
	 * 
	 */
	private transient final java.util.concurrent.Flow.Subscription subscription;
	
	/**
	 * @param subscription
	 */
	public ObjectSubscription(final java.util.concurrent.Flow.Subscription subscription) {
		this.subscription = subscription;
	}

	@Override
	public void request(final long n) {
		subscription.request(n);
	}

	@Override
	public void cancel() {
		subscription.cancel();
	}
}
