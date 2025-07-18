package epf.workflow.management.internal;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jnosql.mapping.document.DocumentTemplate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import epf.util.logging.LogManager;
import epf.workflow.schema.WorkflowDefinition;

@ApplicationScoped
public class WorkflowPersistence {
	
	private transient static final Logger LOGGER = LogManager.getLogger(WorkflowPersistence.class.getName());
	
	@Inject
	transient DocumentTemplate document;
	
	public Optional<WorkflowDefinition> find(final String id, final String version) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		try {
			workflowDefinition = document.find(WorkflowDefinition.class, id);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "find", e);
		}
		return workflowDefinition;
	}
	
	public Optional<WorkflowDefinition> find(final String id) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		try {
			workflowDefinition = document.find(WorkflowDefinition.class, id);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "find", e);
		}
		return workflowDefinition;
	}
	
	public WorkflowDefinition persist(final WorkflowDefinition workflowDefinition) {
		try {
			return document.insert(workflowDefinition);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "persist", e);
			return null;
		}
	}
	
	public WorkflowDefinition merge(final WorkflowDefinition workflowDefinition) {
		try {
			return document.update(workflowDefinition);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "merge", e);
			return null;
		}
	}
}
