package epf.workflow.task.call.spi;

import epf.workflow.task.call.schema.AsyncAPI;

public interface AsyncAPICallService {

	Object call(final AsyncAPI asyncAPI, final Object input) throws Error;
}
