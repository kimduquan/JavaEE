package epf.workflow.task.run;

import epf.workflow.task.run.schema.ContainerLifetime;

public interface ContainerLifetimeService {

	void cleanup(final ContainerLifetime containerLifetime) throws Exception;
}
