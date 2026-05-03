package epf.workflow.task.call;

import epf.workflow.schema.AsyncAPICall;

public interface AsyncAPICallService {

	Object call(final AsyncAPICall asyncAPI, final Object input) throws Exception;
}
