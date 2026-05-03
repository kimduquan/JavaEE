package epf.workflow.task.call;

import epf.workflow.schema.A2ACall;

public interface A2ACallService {

	Object call(final A2ACall call, final Object input) throws Exception;
}
