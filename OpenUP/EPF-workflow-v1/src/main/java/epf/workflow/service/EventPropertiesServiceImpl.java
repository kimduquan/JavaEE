package epf.workflow.service;

import org.eclipse.jnosql.mapping.column.ColumnTemplate;
import org.eclipse.jnosql.mapping.document.DocumentTemplate;
import epf.workflow.schema.EventProperties;
import epf.workflow.spi.EventPropertiesService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventPropertiesServiceImpl implements EventPropertiesService {
	
	@Inject
	transient ColumnTemplate eventRepository;
	
	@Inject
	transient DocumentTemplate eventDataRepository;

	@Override
	public void persist(final EventProperties event) throws Error {
		eventRepository.insert(event);
		eventDataRepository.insert(event.getData());
	}
}
