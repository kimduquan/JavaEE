package epf.workflow.service;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Task;
import epf.workflow.schema.Workflow;
import epf.workflow.spi.TimeoutService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeoutServiceImpl implements TimeoutService {

	@Override
	public Duration getTimeout(Workflow workflow, Task task) throws Error {
		return null;
	}
}
