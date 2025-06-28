package epf.workflow.spi;

import epf.workflow.schema.ShellProcess;
import epf.workflow.schema.Error;
import epf.workflow.schema.ProcessResult;

public interface ShellProcessService {

	ProcessResult run(final ShellProcess shellProcess, final boolean await) throws Error;
}
