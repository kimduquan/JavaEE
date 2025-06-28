package epf.workflow.spi;

import epf.workflow.schema.AsyncAPI;

public interface AsyncAPICallService {

	Object call(final AsyncAPI asyncAPI) throws Error;
}
