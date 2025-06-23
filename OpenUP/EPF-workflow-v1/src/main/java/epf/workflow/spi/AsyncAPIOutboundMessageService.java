package epf.workflow.spi;

import epf.workflow.schema.AsyncAPIOutboundMessage;

public interface AsyncAPIOutboundMessageService {

	void publish(final AsyncAPIOutboundMessage message) throws Exception;
}
