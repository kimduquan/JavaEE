package epf.workflow;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.util.logging.LogManager;
import epf.workflow.event.Event;
import epf.workflow.event.persistence.CallbackStateEvent;
import epf.workflow.event.persistence.EventStateActionEvent;
import epf.workflow.event.persistence.EventStateEvent;
import epf.workflow.event.persistence.WorkflowEvent;
import epf.workflow.internal.NoSQLColumn;
import epf.workflow.schema.event.EventDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WorkflowEventStore {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(WorkflowEventStore.class.getName());
	
	/**
	 * 
	 */
	@Inject
	@RestClient
	transient NoSQLColumn column;

	/**
	 * @param event
	 */
	public void persist(final WorkflowEvent event) {
		event.setId(UUID.randomUUID().toString());
		event.setTime(String.valueOf(Instant.now().toEpochMilli()));
		try {
			column.insert(event);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "persist", e);
		}
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateEvent> findEventStateEvent(final Event event) {
		//final ColumnQuery columnQuery = ColumnQuery.select().from(Naming.Workflow.EVENT).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(EventStateEvent.class.getSimpleName()).build();
		//return template.select(columnQuery);
		//return template.select(EventStateEvent.class).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(EventStateEvent.class.getSimpleName()).stream();
		final List<EventStateEvent> list = new ArrayList<>();
		return list.stream();
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<EventStateActionEvent> findEventStateActionEvent(final Event event) {
		//final ColumnQuery columnQuery = ColumnQuery.select().from(Naming.Workflow.EVENT).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(EventStateActionEvent.class.getSimpleName()).build();
		//return template.select(columnQuery);
		//return template.select(EventStateActionEvent.class).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(EventStateActionEvent.class.getSimpleName()).stream();
		final List<EventStateActionEvent> list = new ArrayList<>();
		return list.stream();
	}
	
	/**
	 * @param event
	 * @return
	 */
	public Stream<CallbackStateEvent> findCallbackStateEvent(final Event event){
		//final ColumnQuery columnQuery = ColumnQuery.select().from(Naming.Workflow.EVENT).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(CallbackStateEvent.class.getSimpleName()).build();
		//return template.select(columnQuery);
		//return template.select(CallbackStateEvent.class).where("source").eq(event.getSource()).and("type").eq(event.getType()).and("name").eq(CallbackStateEvent.class.getSimpleName()).stream();
		final List<CallbackStateEvent> list = new ArrayList<>();
		return list.stream();
	}
	
	/**
	 * @param event
	 */
	public void merge(final WorkflowEvent event) {
		try {
			column.update(event);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "merge", e);
		}
	}
	
	public void remove(final WorkflowEvent event) {
		//final ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(Naming.Workflow.EVENT).where("id").eq(UUID.fromString(event.getId())).and("source").eq(event.getSource()).and("type").eq(event.getType()).build();
		//template.delete(deleteQuery);
		//template.delete(WorkflowEvent.class).where("id").eq(UUID.fromString(event.getId())).and("source").eq(event.getSource()).and("type").eq(event.getType()).execute();
		try {
			column.delete(event.getId());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "remove", e);
		}
	}
	
	/**
	 * @param eventDefinition
	 * @return
	 */
	public long count(final EventDefinition eventDefinition) {
		//final ColumnQuery columnQuery = ColumnQuery.select("COUNT(id)").from(Naming.Workflow.EVENT).where("source").eq(eventDefinition.getSource()).and("type").eq(eventDefinition.getType()).build();
		//Optional<Integer> count = template.singleResult(columnQuery);
		//return count.get();
		//return template.select(WorkflowEvent.class).where("source").eq(eventDefinition.getSource()).and("type").eq(eventDefinition.getType()).stream().count();
		return 0;
	}
}
