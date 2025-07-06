package epf.workflow.task.call;

import epf.workflow.schema.Error;
import epf.workflow.task.call.schema.gRPC;

public interface gRPCCallService {

	Object call(final gRPC grpc, final Object input) throws Error;
}
