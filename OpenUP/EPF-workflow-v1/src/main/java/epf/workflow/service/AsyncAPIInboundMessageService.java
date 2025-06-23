package epf.workflow.service;

import epf.workflow.schema.AsyncAPIInboundMessage;

public interface AsyncAPIInboundMessageService {

	void consume(final AsyncAPIInboundMessage message) throws Exception;
}
