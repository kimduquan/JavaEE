package epf.workflow.service;

import epf.workflow.schema.ContainerProcess;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.ProcessResult;
import epf.workflow.spi.ContainerProcessService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContainerProcessServiceImpl implements ContainerProcessService {

	@Override
	public ProcessResult run(final ContainerProcess containerProcess, final boolean await, final Duration timeout) throws Error {
		return null;
	}
}
