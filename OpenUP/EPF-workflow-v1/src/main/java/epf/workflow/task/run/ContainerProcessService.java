package epf.workflow.task.run;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.run.schema.ContainerProcess;
import epf.workflow.task.run.schema.ProcessResult;

public interface ContainerProcessService {

	ProcessResult run(final ContainerProcess containerProcess, final RuntimeExpressionArguments arguments, final boolean await, final Duration timeout) throws Error;
}
