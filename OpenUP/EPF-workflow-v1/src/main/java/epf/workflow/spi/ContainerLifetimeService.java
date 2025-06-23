package epf.workflow.spi;

import epf.workflow.schema.ContainerLifetime;

public interface ContainerLifetimeService {

	void cleanup(final ContainerLifetime containerLifetime) throws Exception;
}
