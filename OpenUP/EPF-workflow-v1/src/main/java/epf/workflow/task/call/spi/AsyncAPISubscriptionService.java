package epf.workflow.task.call.spi;

import epf.workflow.task.call.schema.AsyncAPISubscription;

public interface AsyncAPISubscriptionService {

	void subscribe(final AsyncAPISubscription subscription) throws Exception;
}
