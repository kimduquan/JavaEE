package epf.workflow;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.naming.Naming;
import epf.workflow.event.Event;
import epf.workflow.event.persistence.CallbackStateEvent;
import epf.workflow.event.persistence.EventStateActionEvent;
import epf.workflow.event.persistence.EventStateEvent;
import epf.workflow.event.persistence.WorkflowEvent;
import epf.workflow.event.schema.EventDefinition;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.mapping.column.ColumnTemplate;

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
	transient ColumnTemplate template;

	/**
	 * @param event
	 */
	public void persist(final WorkflowEvent event) {
		event.setId(UUID.randomUUID().toString());
		event.setTime(String.valueOf(Instant.now().toEpochMilli()));
		template.insert(event);
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateEvent> findEventStateEvent(final Event event) {
		final ColumnQuery columnQuery = ColumnQuery.select().from(Naming.Workflow.EVENT).where("source").eq(event.getSource()).and("type").eq(event.getType()).build();
		return template.select(columnQuery);
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateActionEvent> findEventStateActionEvent(final Event event) {
		final ColumnQuery columnQuery = ColumnQuery.select().from(Naming.Workflow.EVENT).where("source").eq(event.getSource()).and("type").eq(event.getType()).build();
		return template.select(columnQuery);
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<CallbackStateEvent> findCallbackStateEvent(final Event event){
		final ColumnQuery columnQuery = ColumnQuery.select().from(Naming.Workflow.EVENT).where("source").eq(event.getSource()).and("type").eq(event.getType()).build();
		return template.select(columnQuery);
	}
	
	/**
	 * @param event
	 */
	public void merge(final WorkflowEvent event) {
		template.update(event);
	}
	
	public void remove(final WorkflowEvent event) {
		final ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(Naming.Workflow.EVENT).where("id").eq(UUID.fromString(event.getId())).and("source").eq(event.getSource()).and("type").eq(event.getType()).build();
		template.delete(deleteQuery);
	}
	
	/**
	 * @param eventDefinition
	 * @return
	 */
	public long count(final EventDefinition eventDefinition) {
		final ColumnQuery columnQuery = ColumnQuery.select("COUNT(id)").from(Naming.Workflow.EVENT).where("source").eq(eventDefinition.getSource()).and("type").eq(eventDefinition.getType()).build();
		Optional<Integer> count = template.singleResult(columnQuery);
		return count.get();
	}
}
