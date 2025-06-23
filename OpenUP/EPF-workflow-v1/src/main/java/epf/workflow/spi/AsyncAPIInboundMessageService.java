package epf.workflow.spi;

import epf.workflow.schema.AsyncAPIInboundMessage;

public interface AsyncAPIInboundMessageService {

	void consume(final AsyncAPIInboundMessage message) throws Exception;
}
