package epf.workflow.task.spi;

import epf.workflow.schema.OpenAPI;
import epf.workflow.schema.Error;

public interface OpenAPICallService {

	Object call(final OpenAPI openAPI, final Object input) throws Error;
}
