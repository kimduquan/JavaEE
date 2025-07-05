package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.task.call.schema.HTTP;

public interface HTTPCallService {

	Object call(final HTTP http, final Object input) throws Error;
}
