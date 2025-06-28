package epf.workflow.api;

import epf.workflow.schema.WorkflowDescriptor;
import java.util.List;
import epf.workflow.event.TaskLifecycleEvent;
import epf.workflow.event.WorkflowLifecycleEvent;
import epf.workflow.schema.Error;
import epf.workflow.schema.TaskDescriptor;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

public interface WorkflowAPI {

	@GET
	@Path("workflows")
	List<WorkflowDescriptor> getWorkflows() throws Error;
	
	@POST
	@Path("workflows")
	Object start(
			@QueryParam("name")
			final String name,
			@QueryParam("namespace")
			final String namespace,
			@QueryParam("version")
			final String version,
			final Object input) throws Error;
	
	@GET
	@Path("workflows/{name}/events")
	List<WorkflowLifecycleEvent> getWorkflowLifecycleEvents() throws Error;
	
	@GET
	@Path("workflows/{name}/tasks")
	List<TaskDescriptor> getWorkflowTasks(
			@PathParam("name") 
			final String name) throws Error;
	
	@POST
	@Path("workflows/{name}/tasks")
	Object startTask(
			@PathParam("name") 
			final String name,
			@QueryParam("task") 
			final String task,
			final Object input) throws Error;
	
	@GET
	@Path("workflows/{name}/tasks/{task}/events")
	List<TaskLifecycleEvent> getTaskLifecycleEvents() throws Error;
}
