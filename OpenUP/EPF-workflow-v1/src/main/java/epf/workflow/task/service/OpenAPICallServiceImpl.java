package epf.workflow.task.service;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import epf.util.json.ext.JsonUtil;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeError;
import epf.workflow.task.schema.OpenAPI;
import epf.workflow.task.spi.OpenAPICallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OpenAPICallServiceImpl implements OpenAPICallService {
	
	@Inject
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;

	@Override
	public Object call(final OpenAPI openAPI, final Object input) throws Error {
		try {
			final String endpointUri = String.format("rest-openapi:%s#%s", openAPI.getDocument().getEndpoint().getUri(), openAPI.getOperationId());
			final Object body = JsonUtil.toJsonValue(input);
			final Object entity = producer.requestBody(endpointUri, body);
			return entity;
		}
		catch(Exception ex) {
			throw new RuntimeError(ex);
		}
	}

}
