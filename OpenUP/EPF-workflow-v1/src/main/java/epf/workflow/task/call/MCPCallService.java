package epf.workflow.task.call;

import epf.workflow.schema.MCPCall;

public interface MCPCallService {

	Object call(final MCPCall call, final Object input) throws Exception;
}
