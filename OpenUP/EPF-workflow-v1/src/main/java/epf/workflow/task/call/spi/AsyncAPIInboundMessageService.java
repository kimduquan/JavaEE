package epf.workflow.task.call.spi;

import epf.workflow.task.call.schema.AsyncAPIInboundMessage;

public interface AsyncAPIInboundMessageService {

	void consume(final AsyncAPIInboundMessage message) throws Exception;
}
