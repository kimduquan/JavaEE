package epf.workflow.internal;

import java.util.Arrays;
import epf.workflow.schema.Error;
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
	public void persist(final EventConsumptionStrategy eventConsumptionStrategy) throws Error {
		if(eventConsumptionStrategy.getOne() != null) {
			eventFilterService.persist(eventConsumptionStrategy.getOne());
		}
		if(eventConsumptionStrategy.getAll() != null) {
			eventFilterService.persist(Arrays.asList(eventConsumptionStrategy.getAll()));
		}
		if(eventConsumptionStrategy.getAny() != null) {
			eventFilterService.persist(Arrays.asList(eventConsumptionStrategy.getAny()));
		}
	}

}
