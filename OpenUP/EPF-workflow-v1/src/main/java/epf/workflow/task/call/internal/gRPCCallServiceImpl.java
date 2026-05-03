package epf.workflow.task.call.internal;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import epf.util.json.ext.JsonUtil;
import epf.workflow.schema.gRPCCall;
import epf.workflow.task.call.gRPCCallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class gRPCCallServiceImpl implements gRPCCallService {
	
	@Inject
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;

	@Override
	public Object call(final gRPCCall grpc, final Object input) throws Exception {
		final String endpointUri = String.format("grpc:%s:%d/%s?method=%s", grpc.getService().getHost(), grpc.getService().getPort(), grpc.getService().getName(), grpc.getMethod());
		final Object body = JsonUtil.toJsonValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return entity;
	}

}
