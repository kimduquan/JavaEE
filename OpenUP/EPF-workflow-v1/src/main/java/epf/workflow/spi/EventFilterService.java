package epf.workflow.spi;

import java.util.List;
import epf.workflow.schema.EventFilter;
import epf.workflow.schema.EventProperties;

public interface EventFilterService {
	
	void persist(final EventFilter eventFilter) throws Exception;
	void persist(final List<EventFilter> eventFilters) throws Exception;
	List<EventFilter> findEventFilters(final EventProperties event) throws Exception;
}
