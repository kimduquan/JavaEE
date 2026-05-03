package epf.workflow.task.call.internal;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import epf.util.json.ext.JsonUtil;
import epf.workflow.schema.OpenAPICall;
import epf.workflow.task.call.OpenAPICallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OpenAPICallServiceImpl implements OpenAPICallService {
	
	@Inject
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;

	@Override
	public Object call(final OpenAPICall openAPI, final Object input) throws Exception {
		final String endpointUri = String.format("rest-openapi:%s#%s", openAPI.getDocument().getEndpoint().getUri(), openAPI.getOperationId());
		final Object body = JsonUtil.toJsonValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return entity;
	}

}
