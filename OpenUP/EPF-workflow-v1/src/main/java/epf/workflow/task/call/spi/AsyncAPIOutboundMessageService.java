package epf.workflow.task.call.spi;

import epf.workflow.task.call.schema.AsyncAPIOutboundMessage;

public interface AsyncAPIOutboundMessageService {

	void publish(final AsyncAPIOutboundMessage message) throws Exception;
}
