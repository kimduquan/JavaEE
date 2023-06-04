package epf.workflow;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.workflow.event.Event;
import epf.workflow.event.persistence.CallbackStateEvent;
import epf.workflow.event.persistence.EventStateActionEvent;
import epf.workflow.event.persistence.EventStateEvent;
import epf.workflow.event.persistence.WorkflowEvent;
import epf.workflow.schema.event.EventDefinition;
import jakarta.nosql.column.ColumnTemplate;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WorkflowEventStore {
	
	/**
	 * 
	 */
	@Inject
	transient ColumnTemplate column;

	/**
	 * @param event
	 */
	public void persist(final WorkflowEvent event) {
		event.setId(UUID.randomUUID().toString());
		event.setTime(String.valueOf(Instant.now().toEpochMilli()));
		column.insert(event);
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateEvent> findEventStateEvent(final Event event) {
		return column.select(EventStateEvent.class).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(EventStateEvent.class.getSimpleName()).stream();
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateActionEvent> findEventStateActionEvent(final Event event) {
		return column.select(EventStateActionEvent.class).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(EventStateActionEvent.class.getSimpleName()).stream();
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<CallbackStateEvent> findCallbackStateEvent(final Event event){
		return column.select(CallbackStateEvent.class).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(CallbackStateEvent.class.getSimpleName()).stream();
	}
	
	/**
	 * @param event
	 */
	public void merge(final WorkflowEvent event) {
		column.update(event);
	}
	
	public void remove(final WorkflowEvent event) {
		column.delete(event.getClass()).where("id").eq(UUID.fromString(event.getId())).and("source").eq(event.getSource()).and("type").eq(event.getType()).execute();
	}
	
	/**
	 * @param eventDefinition
	 * @return
	 */
	public long count(final EventDefinition eventDefinition) {
		return column.select(eventDefinition.getClass()).where("source").eq(eventDefinition.getSource()).and("type").eq(eventDefinition.getType()).stream().count();
	}
}
