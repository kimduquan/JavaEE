package epf.workflow.spi;

import epf.workflow.schema.ProcessResult;
import epf.workflow.schema.ScriptProcess;

public interface ScriptProcessService {

	ProcessResult run(final ScriptProcess scriptProcess, final boolean await) throws Error;
}
