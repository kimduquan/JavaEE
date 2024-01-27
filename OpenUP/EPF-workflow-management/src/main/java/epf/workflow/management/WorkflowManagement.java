package epf.workflow.management;

import java.io.InputStream;
import java.util.Optional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.workflow.management.internal.WorkflowCache;
import epf.workflow.management.internal.WorkflowLink;
import epf.workflow.management.internal.WorkflowPersistence;
import epf.workflow.schema.WorkflowDefinition;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.Workflow.WORKFLOW_MANAGEMENT)
public class WorkflowManagement  {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowPersistence persistence;
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	transient WorkflowCache cache;
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@Inject
	transient Validator validator;
	
	private ResponseBuilder getWorkflowLink(final WorkflowDefinition workflowDefinition) {
		final Link link = WorkflowLink.getWorkflowLink(0, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()));
		return Response.ok().links(link);
	}
	
	private WorkflowDefinition findWorkflowDefinition(final String workflow, final String version) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		if(version != null) {
			workflowDefinition = Optional.ofNullable(cache.get(workflow, version));
			if(!workflowDefinition.isPresent()) {
				workflowDefinition = persistence.find(workflow, version);
			}
		}
		else {
			workflowDefinition = Optional.ofNullable(cache.get(workflow));
			if(!workflowDefinition.isPresent()) {
				workflowDefinition = persistence.find(workflow);
			}
		}
		return workflowDefinition.orElseThrow(NotFoundException::new);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response newWorkflowDefinition(final InputStream body) throws Exception {
		WorkflowDefinition workflowDefinition = null;
		try(Jsonb jsonb = JsonbBuilder.create()){
			workflowDefinition = jsonb.fromJson(body, WorkflowDefinition.class);
		}
		catch(Exception ex) {
			throw new BadRequestException();
		}
		if(!validator.validate(workflowDefinition).isEmpty()) {
			throw new BadRequestException();
		}
		final WorkflowDefinition newWorkflowDefinition = persistence.persist(workflowDefinition);
		if(workflowDefinition.getVersion() != null) {
			cache.put(newWorkflowDefinition.getId(), workflowDefinition.getVersion(), workflowDefinition);
		}
		else {
			cache.put(newWorkflowDefinition.getId(), workflowDefinition);
		}
		return getWorkflowLink(newWorkflowDefinition).build();
	}

	@GET
	@Path("{workflow}")
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response getWorkflowDefinition(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version) throws Exception {
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		try(Jsonb jsonb = JsonbBuilder.create()){
			return Response.ok(jsonb.toJson(workflowDefinition), MediaType.APPLICATION_JSON).build();
		}
	}
}
