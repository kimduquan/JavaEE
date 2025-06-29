package epf.workflow.spi;

import epf.workflow.schema.AsyncAPI;

public interface AsyncAPICallService {

	Object call(final AsyncAPI asyncAPI, final Object input) throws Error;
}
