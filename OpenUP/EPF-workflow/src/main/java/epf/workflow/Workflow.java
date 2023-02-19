package epf.workflow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import epf.naming.Naming;
import epf.workflow.schema.WorkflowDefinition;
import jakarta.nosql.mapping.document.DocumentTemplate;

/**
 * @author PC
 *
 */
@ApplicationScoped
@ApplicationPath(Naming.WORKFLOW)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Workflow {
	
	/**
	 * 
	 */
	@Inject
	DocumentTemplate document;

	/**
	 * @return
	 */
	@POST
	public WorkflowDefinition newWorkflowDefinition(@Valid final WorkflowDefinition workflowDefinition) {
		return document.insert(workflowDefinition);
	}
}
