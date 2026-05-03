package epf.workflow.task.call;

import epf.workflow.schema.HTTPCall;

public interface HTTPCallService {

	Object call(final HTTPCall http, final Object input) throws Exception;
}
