package epf.workflow;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.util.logging.LogManager;
import epf.workflow.internal.NoSQLDocument;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WorkflowPersistence {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(WorkflowPersistence.class.getName());
	
	/**
	 * 
	 */
	@Inject
	@RestClient
	transient NoSQLDocument document;
	
	/**
	 * @param id
	 * @param version
	 * @return
	 */
	public Optional<WorkflowDefinition> find(final String id, final String version) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		try {
			workflowDefinition = Optional.ofNullable(document.find(id).readEntity(WorkflowDefinition.class));
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "find", e);
		}
		return workflowDefinition;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Optional<WorkflowDefinition> find(final String id) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		try {
			workflowDefinition = Optional.ofNullable(document.find(id).readEntity(WorkflowDefinition.class));
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "find", e);
		}
		return workflowDefinition;
	}
	
	/**
	 * @param workflowDefinition
	 * @return
	 */
	public WorkflowDefinition persist(final WorkflowDefinition workflowDefinition) {
		try {
			return document.insert(workflowDefinition).readEntity(WorkflowDefinition.class);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "persist", e);
			return null;
		}
	}
	
	/**
	 * @param workflowInstance
	 * @return
	 */
	public WorkflowInstance persist(final WorkflowInstance workflowInstance) {
		if(workflowInstance.getUri() != null) {
			workflowInstance.setId(workflowInstance.getUri().toString());
		}
		else {
			workflowInstance.setId(UUID.randomUUID().toString());
		}
		try {
			return document.insert(workflowInstance).readEntity(WorkflowInstance.class);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "persist", e);
			return null;
		}
	}
	
	/**
	 * @param workflowInstance
	 */
	public void merge(final WorkflowInstance workflowInstance) {
		try {
			document.update(workflowInstance);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "merge", e);
		}
	}
	
	/**
	 * @param id
	 * @return
	 */
	public WorkflowInstance getInstance(final String id) {
		try {
			return document.find(id).readEntity(WorkflowInstance.class);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "getInstance", e);
			return null;
		}
	}
	
	/**
	 * @param workflowInstance
	 */
	public void remove(final WorkflowInstance workflowInstance) {
		try {
			document.delete(workflowInstance.getId());
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "remove", e);
		}
	}
}
