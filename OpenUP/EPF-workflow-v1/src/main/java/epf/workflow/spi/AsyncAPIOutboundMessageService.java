package epf.workflow.spi;

import epf.workflow.task.schema.AsyncAPIOutboundMessage;

public interface AsyncAPIOutboundMessageService {

	void publish(final AsyncAPIOutboundMessage message) throws Exception;
}
