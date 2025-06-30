package epf.workflow.service;

import epf.workflow.schema.AsyncAPI;
import epf.workflow.schema.AsyncAPICall;
import epf.workflow.schema.Error;
import epf.workflow.schema.HTTP;
import epf.workflow.schema.HTTPCall;
import epf.workflow.schema.OpenAPI;
import epf.workflow.schema.OpenAPICall;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.gRPC;
import epf.workflow.schema.gRPCCall;
import epf.workflow.spi.AsyncAPICallService;
import epf.workflow.spi.CallService;
import epf.workflow.spi.HTTPCallService;
import epf.workflow.spi.OpenAPICallService;
import epf.workflow.spi.gRPCCallService;
import epf.workflow.task.schema.CallTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CallServiceImpl implements CallService {
	
	@Inject
	transient AsyncAPICallService asyncAPICallService;
	
	@Inject
	transient HTTPCallService httpCallService;
	
	@Inject
	transient OpenAPICallService openAPICallService;
	
	@Inject
	transient gRPCCallService gRPCCallService;

	@Override
	public Object call(final RuntimeExpressionArguments arguments, final CallTask task, final Object taskInput) throws Error {
		Object output = null;
		if(task.getCall() instanceof AsyncAPICall) {
			final AsyncAPI asyncAPI = ((AsyncAPICall)task.getCall()).getWith();
			output = asyncAPICallService.call(asyncAPI, taskInput);
		}
		else if(task.getCall() instanceof HTTPCall) {
			final HTTP http = ((HTTPCall)task.getCall()).getWith();
			output = httpCallService.call(http, taskInput);
		}
		else if(task.getCall() instanceof OpenAPICall) {
			final OpenAPI openAPI = ((OpenAPICall)task.getCall()).getWith();
			output = openAPICallService.call(openAPI, taskInput);
		}
		else if(task.getCall() instanceof gRPCCall) {
			final gRPC grpc = ((gRPCCall)task.getCall()).getWith();
			output = gRPCCallService.call(grpc, taskInput);
		}
		return output;
	}

}
