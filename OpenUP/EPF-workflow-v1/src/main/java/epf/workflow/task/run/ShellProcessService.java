package epf.workflow.task.run;

import epf.workflow.schema.Duration;
import epf.workflow.schema.ProcessResult;
import epf.workflow.schema.ShellProcess;

public interface ShellProcessService {

	ProcessResult run(final ShellProcess shellProcess, final boolean await, final Duration timeout) throws Exception;
}
