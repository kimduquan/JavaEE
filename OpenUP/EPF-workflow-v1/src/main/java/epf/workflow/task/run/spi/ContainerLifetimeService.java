package epf.workflow.task.run.spi;

import epf.workflow.task.run.schema.ContainerLifetime;

public interface ContainerLifetimeService {

	void cleanup(final ContainerLifetime containerLifetime) throws Exception;
}
