package epf.workflow.spi;

import epf.workflow.schema.ContainerProcess;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.ProcessResult;

public interface ContainerProcessService {

	ProcessResult run(final ContainerProcess containerProcess, final boolean await, final Duration timeout) throws Error;
}
