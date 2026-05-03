package epf.workflow.task.call;

import epf.workflow.schema.OpenAPICall;

public interface OpenAPICallService {

	Object call(final OpenAPICall openAPI, final Object input) throws Exception;
}
