package epf.workflow;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import epf.util.json.ext.JsonUtil;
import epf.workflow.schema.gRPC;
import epf.workflow.schema.HTTP;
import epf.workflow.schema.OpenAPI;
import epf.workflow.schema.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;

@ApplicationScoped
public class Integration {
	
	@Inject
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;
	
	public Object gRPC(final Instance instance, final Task task, final gRPC gRPC, final JsonValue input) throws Exception {
		final String endpointUri = String.format("grpc:%s:%d/%s?method=%s", gRPC.getService().getHost(), gRPC.getService().getPort(), gRPC.getService().getName(), gRPC.getMethod());
		final Object body = JsonUtil.asValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return entity;
	}
	
	public Object http(final Instance instance, final Task task, final HTTP http, final JsonValue input) throws Exception {
		final String endpointUri = http.getEndpoint().isLeft() ? http.getEndpoint().getLeft() : http.getEndpoint().getRight().getUri();
		final Object body = JsonUtil.asValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return entity;
	}
	
	public Object openapi(final Instance instance, final Task task, final OpenAPI openapi, final JsonValue input) throws Exception {
		final String endpointUri = String.format("rest-openapi:%s#%s", openapi.getDocument().getEndpoint().getUri(), openapi.getOperationId());
		final Object body = JsonUtil.asValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return entity;
	}
}
