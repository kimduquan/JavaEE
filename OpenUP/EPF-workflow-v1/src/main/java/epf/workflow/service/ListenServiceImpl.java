package epf.workflow.service;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.workflow.schema.Error;
import epf.workflow.schema.EventProperties;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.EventConsumptionStrategyService;
import epf.workflow.spi.EventFilterService;
import epf.workflow.spi.EventPropertiesService;
import epf.workflow.spi.ListenService;
import epf.workflow.task.schema.ListenTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ListenServiceImpl implements ListenService {
	
	@Inject
	transient EventPropertiesService eventPropertiesService;
	
	@Inject
	transient EventFilterService eventFilterService;
	
	@Inject
	transient EventConsumptionStrategyService eventConsumptionStrategyService;

	@Override
	public Object listen(final RuntimeExpressionArguments arguments, final ListenTask task, final Object taskInput) throws Error {
		eventConsumptionStrategyService.persist(task.getListen().getTo());
		return taskInput;
	}

	@Incoming(Naming.Workflow.EVENTS)
	public void listen(final EventProperties event) throws Error {
		eventPropertiesService.persist(event);
		eventFilterService.findEventFilters(event);
	}
}
