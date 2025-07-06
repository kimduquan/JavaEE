package epf.workflow.task.call;

import epf.workflow.task.call.schema.AsyncAPIOutboundMessage;

public interface AsyncAPIOutboundMessageService {

	void publish(final AsyncAPIOutboundMessage message) throws Exception;
}
