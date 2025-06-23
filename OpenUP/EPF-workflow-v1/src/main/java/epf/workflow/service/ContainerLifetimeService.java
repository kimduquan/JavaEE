package epf.workflow.service;

import epf.workflow.schema.ContainerLifetime;

public interface ContainerLifetimeService {

	void cleanup(final ContainerLifetime containerLifetime) throws Exception;
}
