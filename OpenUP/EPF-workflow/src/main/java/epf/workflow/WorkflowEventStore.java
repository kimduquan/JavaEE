package epf.workflow;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import jakarta.enterprise.context.ApplicationScoped;
import epf.workflow.event.Event;
import epf.workflow.event.persistence.CallbackStateEvent;
import epf.workflow.event.persistence.EventStateActionEvent;
import epf.workflow.event.persistence.EventStateEvent;
import epf.workflow.event.persistence.WorkflowEvent;
import epf.workflow.schema.event.EventDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WorkflowEventStore {
	
	/**
	 * @param event
	 */
	public void persist(final WorkflowEvent event) {
		event.setId(UUID.randomUUID().toString());
		event.setTime(String.valueOf(Instant.now().toEpochMilli()));
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateEvent> findEventStateEvent(final Event event) {
		final List<EventStateEvent> list = new ArrayList<>();
		return list.stream();
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateActionEvent> findEventStateActionEvent(final Event event) {
		final List<EventStateActionEvent> list = new ArrayList<>();
		return list.stream();
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<CallbackStateEvent> findCallbackStateEvent(final Event event){
		final List<CallbackStateEvent> list = new ArrayList<>();
		return list.stream();
	}
	
	/**
	 * @param event
	 */
	public void merge(final WorkflowEvent event) {
		
	}
	
	public void remove(final WorkflowEvent event) {
		
	}
	
	/**
	 * @param eventDefinition
	 * @return
	 */
	public long count(final EventDefinition eventDefinition) {
		return 0;
	}
}
