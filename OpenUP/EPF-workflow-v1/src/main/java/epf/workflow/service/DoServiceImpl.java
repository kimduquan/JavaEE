package epf.workflow.service;

import epf.workflow.schema.Do;
import epf.workflow.spi.DoService;
import epf.workflow.spi.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DoServiceImpl implements DoService {
	
	@Inject
	transient TaskService taskService; 

	@Override
	public void _do(final Do _do) throws Exception {
		_do.getDo_().forEach((name, task) -> {
			
		});
	}
}
