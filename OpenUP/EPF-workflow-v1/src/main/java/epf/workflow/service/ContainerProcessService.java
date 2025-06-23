package epf.workflow.service;

import epf.workflow.schema.ContainerProcess;

public interface ContainerProcessService {

	void run(final ContainerProcess containerProcess) throws Exception;
}
