package epf.workflow.task.call.internal;

import epf.workflow.schema.A2A;
import epf.workflow.schema.A2ACall;
import epf.workflow.schema.AsyncAPI;
import epf.workflow.schema.AsyncAPICall;
import epf.workflow.schema.Call;
import epf.workflow.schema.HTTP;
import epf.workflow.schema.HTTPCall;
import epf.workflow.schema.MCP;
import epf.workflow.schema.MCPCall;
import epf.workflow.schema.OpenAPI;
import epf.workflow.schema.OpenAPICall;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.gRPC;
import epf.workflow.schema.gRPCCall;
import epf.workflow.task.CallService;
import epf.workflow.task.call.A2ACallService;
import epf.workflow.task.call.AsyncAPICallService;
import epf.workflow.task.call.HTTPCallService;
import epf.workflow.task.call.MCPCallService;
import epf.workflow.task.call.OpenAPICallService;
import epf.workflow.task.call.gRPCCallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CallServiceImpl implements CallService {
	
	@Inject
	transient A2ACallService a2aCallService;
	
	@Inject
	transient MCPCallService mcpCallService;
	
	@Inject
	transient AsyncAPICallService asyncAPICallService;
	
	@Inject
	transient HTTPCallService httpCallService;
	
	@Inject
	transient OpenAPICallService openAPICallService;
	
	@Inject
	transient gRPCCallService gRPCCallService;

	@Override
	public Object call(final RuntimeExpressionArguments arguments, final Call<?> task, final Object taskInput) throws Exception {
		Object output = null;
		if(task instanceof A2A) {
			final A2ACall a2aCall = ((A2A)task).getWith();
			output = a2aCallService.call(a2aCall, taskInput);
		}
		else if(task instanceof AsyncAPI) {
			final AsyncAPICall asyncAPI = ((AsyncAPI)task).getWith();
			output = asyncAPICallService.call(asyncAPI, taskInput);
		}
		else if(task instanceof gRPC) {
			final gRPCCall grpc = ((gRPC)task).getWith();
			output = gRPCCallService.call(grpc, taskInput);
		}
		else if(task instanceof HTTP) {
			final HTTPCall http = ((HTTP)task).getWith();
			output = httpCallService.call(http, taskInput);
		}
		else if(task instanceof MCP) {
			final MCPCall mcp = ((MCP)task).getWith();
			output = mcpCallService.call(mcp, taskInput);
		}
		else if(task instanceof OpenAPI) {
			final OpenAPICall openAPI = ((OpenAPI)task).getWith();
			output = openAPICallService.call(openAPI, taskInput);
		}
		return output;
	}

}
