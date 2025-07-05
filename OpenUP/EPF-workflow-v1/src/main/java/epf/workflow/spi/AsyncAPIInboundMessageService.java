package epf.workflow.spi;

import epf.workflow.task.schema.AsyncAPIInboundMessage;

public interface AsyncAPIInboundMessageService {

	void consume(final AsyncAPIInboundMessage message) throws Exception;
}
