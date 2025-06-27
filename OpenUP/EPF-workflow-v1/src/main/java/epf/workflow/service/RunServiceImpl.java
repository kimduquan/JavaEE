package epf.workflow.service;

import epf.workflow.spi.ContainerProcessService;
import epf.workflow.spi.RunService;
import epf.workflow.task.schema.Run;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RunServiceImpl implements RunService {
	
	@Inject
	transient ContainerProcessService containerProcessService;

	@Override
	public void run(final Run run) throws Exception {
		if(run.getRun().getContainer() != null) {
			containerProcessService.run(run.getRun().getContainer());
		}
	}
}
