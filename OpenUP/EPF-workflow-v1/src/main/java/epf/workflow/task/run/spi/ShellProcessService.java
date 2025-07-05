package epf.workflow.task.run.spi;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.task.run.schema.ProcessResult;
import epf.workflow.task.run.schema.ShellProcess;

public interface ShellProcessService {

	ProcessResult run(final ShellProcess shellProcess, final boolean await, final Duration timeout) throws Error;
}
