package epf.workflow.spi;

import epf.workflow.schema.EventConsumptionStrategy;

public interface EventConsumptionStrategyService {

	void persist(final EventConsumptionStrategy eventConsumptionStrategy) throws Exception;
}
