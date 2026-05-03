package epf.workflow.internal;

import epf.workflow.schema.EventConsumptionStrategy;
import epf.workflow.spi.EventConsumptionStrategyService;
import epf.workflow.spi.EventFilterService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventConsumptionStrategyServiceImpl implements EventConsumptionStrategyService {
	
	@Inject
	transient EventFilterService eventFilterService;

	@Override
	public void persist(final EventConsumptionStrategy eventConsumptionStrategy) throws Exception {
		if(eventConsumptionStrategy.getOne() != null) {
			eventFilterService.persist(eventConsumptionStrategy.getOne());
		}
		if(eventConsumptionStrategy.getAll() != null) {
			eventFilterService.persist(eventConsumptionStrategy.getAll());
		}
		if(eventConsumptionStrategy.getAny() != null) {
			eventFilterService.persist(eventConsumptionStrategy.getAny());
		}
	}

}
