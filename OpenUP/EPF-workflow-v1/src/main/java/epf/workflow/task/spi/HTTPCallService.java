package epf.workflow.task.spi;

import epf.workflow.schema.HTTP;
import epf.workflow.schema.Error;

public interface HTTPCallService {

	Object call(final HTTP http, final Object input) throws Error;
}
