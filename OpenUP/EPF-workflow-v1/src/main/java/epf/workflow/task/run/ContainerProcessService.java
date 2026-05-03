package epf.workflow.task.run;

import epf.workflow.schema.Duration;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.ContainerProcess;
import epf.workflow.schema.ProcessResult;

public interface ContainerProcessService {

	ProcessResult run(final ContainerProcess containerProcess, final RuntimeExpressionArguments arguments, final boolean await, final Duration timeout) throws Exception;
}
