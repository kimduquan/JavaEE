package epf.workflow.service;

import org.eclipse.jnosql.mapping.document.DocumentTemplate;
import epf.event.schema.Event;
import epf.workflow.schema.EventProperties;
import epf.workflow.spi.EventPropertiesService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventPropertiesServiceImpl implements EventPropertiesService {
	
	@Inject
	transient DocumentTemplate eventStore;

	@Override
	public void persist(final EventProperties eventProperties) throws Error {
		final Event event = new Event();
		event.setId(eventProperties.getId());
		event.setSource(eventProperties.getSource());
		event.setSubject(eventProperties.getSubject());
		event.setTime(eventProperties.getTime());
		event.setType(eventProperties.getType());
		event.setData(eventProperties.getData());
		event.setDataContentType(eventProperties.getDatacontenttype());
		event.setDataSchema(eventProperties.getDataschema());
		eventStore.insert(event);
	}
}
