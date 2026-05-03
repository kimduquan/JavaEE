package epf.workflow.task.call;

import epf.workflow.schema.gRPCCall;

public interface gRPCCallService {

	Object call(final gRPCCall grpc, final Object input) throws Exception;
}
