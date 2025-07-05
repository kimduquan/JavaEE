package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.task.schema.gRPC;

public interface gRPCCallService {

	Object call(final gRPC grpc, final Object input) throws Error;
}
