package epf.workflow.task.run;

import epf.workflow.schema.ContainerLifetime;

public interface ContainerLifetimeService {

	void cleanup(final ContainerLifetime containerLifetime) throws Exception;
}
