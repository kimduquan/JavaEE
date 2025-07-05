package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.task.schema.OpenAPI;

public interface OpenAPICallService {

	Object call(final OpenAPI openAPI, final Object input) throws Error;
}
