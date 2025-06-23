package epf.workflow.spi;

import epf.workflow.schema.AsyncAPISubscription;

public interface AsyncAPISubscriptionService {

	void subscribe(final AsyncAPISubscription subscription) throws Exception;
}
