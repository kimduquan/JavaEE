package epf.workflow;

import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.workflow.schema.WorkflowDefinition;
import jakarta.nosql.document.DocumentQuery;
import jakarta.nosql.mapping.document.DocumentTemplate;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WorkflowPersistence {
	
	/**
	 * 
	 */
	@Inject
	transient DocumentTemplate document;
	
	/**
	 * @param id
	 * @param version
	 * @return
	 */
	public WorkflowDefinition find(final String id, final String version) {
		final DocumentQuery query = DocumentQuery.select().from("WorkflowDefinition").where("id").eq(id).and("version").eq(version).build();
		final Optional<WorkflowDefinition> workflowDefinition = document.singleResult(query);
		return workflowDefinition.get();
	}
	
	/**
	 * @param id
	 * @return
	 */
	public WorkflowDefinition find(final String id) {
		return document.find(WorkflowDefinition.class, id).get();
	}
	
	/**
	 * @param workflowDefinition
	 * @return
	 */
	public WorkflowDefinition persist(final WorkflowDefinition workflowDefinition) {
		return document.insert(workflowDefinition);
	}
	
	/**
	 * @param workflowInstance
	 * @return
	 */
	public WorkflowInstance persist(final WorkflowInstance workflowInstance) {
		workflowInstance.setId(UUID.randomUUID().toString());
		return document.insert(workflowInstance);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public WorkflowInstance getInstance(final String id) {
		return document.find(WorkflowInstance.class, id).get();
	}
	
	/**
	 * @param workflowInstance
	 */
	public void remove(final WorkflowInstance workflowInstance) {
		document.delete(WorkflowInstance.class, workflowInstance.getId());
	}
}
