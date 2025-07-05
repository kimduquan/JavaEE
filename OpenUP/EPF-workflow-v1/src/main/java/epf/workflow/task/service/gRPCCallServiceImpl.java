package epf.workflow.task.service;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import epf.util.json.ext.JsonUtil;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeError;
import epf.workflow.task.schema.gRPC;
import epf.workflow.task.spi.gRPCCallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class gRPCCallServiceImpl implements gRPCCallService {
	
	@Inject
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;

	@Override
	public Object call(final gRPC grpc, final Object input) throws Error {
		try {
			final String endpointUri = String.format("grpc:%s:%d/%s?method=%s", grpc.getService().getHost(), grpc.getService().getPort(), grpc.getService().getName(), grpc.getMethod());
			final Object body = JsonUtil.toJsonValue(input);
			final Object entity = producer.requestBody(endpointUri, body);
			return entity;
		}
		catch(Exception ex) {
			throw new RuntimeError(ex);
		}
	}

}
