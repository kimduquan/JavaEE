package epf.workflow.task.internal;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.workflow.schema.EventProperties;
import epf.workflow.schema.Listen;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.EventConsumptionStrategyService;
import epf.workflow.spi.EventFilterService;
import epf.workflow.spi.EventPropertiesService;
import epf.workflow.task.ListenService;
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
	public Object listen(final RuntimeExpressionArguments arguments, final Listen task, final Object taskInput) throws Exception {
		eventConsumptionStrategyService.persist(task.getListen().getTo());
		return taskInput;
	}

	@Incoming(Naming.Workflow.EVENTS)
	public void listen(final EventProperties event) throws Exception {
		eventPropertiesService.persist(event);
		eventFilterService.findEventFilters(event);
	}
}
