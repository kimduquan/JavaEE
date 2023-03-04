package epf.workflow;

import java.util.UUID;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.workflow.event.Event;
import epf.workflow.event.persistence.WorkflowEvent;
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
		template.insert(event);
	}
	
	/**
	 * @param <T>
	 * @param cls
	 * @param event
	 * @return
	 */
	public <T extends WorkflowEvent> Stream<T> find(final Event event){
		final ColumnQuery columnQuery = ColumnQuery.select().from("Event").where("workflowDefinition").eq(event.getSubject()).and("source").eq(event.getSource()).and("type").eq(event.getType()).build();
		return template.select(columnQuery);
	}
	
	/**
	 * @param event
	 */
	public void merge(final WorkflowEvent event) {
		template.update(event);
	}
}
