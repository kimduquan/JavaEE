package epf.workflow.spi;

import epf.workflow.schema.gRPC;
import epf.workflow.schema.Error;

public interface gRPCCallService {

	Object call(final gRPC grpc, final Object input) throws Error;
}
