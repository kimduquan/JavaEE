package epf.workflow.spi;

import epf.workflow.schema.Duration;
import epf.workflow.schema.ScriptProcess;

public interface ScriptProcessService {

	Object run(final ScriptProcess scriptProcess, final boolean await, final Duration timeout) throws Error;
}
