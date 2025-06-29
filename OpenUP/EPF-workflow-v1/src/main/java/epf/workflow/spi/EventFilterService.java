package epf.workflow.spi;

import java.util.List;
import epf.workflow.schema.EventFilter;
import epf.workflow.schema.EventProperties;

public interface EventFilterService {

	List<EventFilter> getEventFilters(final EventProperties event) throws Error;
}
