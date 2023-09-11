package epf.nosql.persistence;

import java.io.InputStream;
import epf.workflow.schema.WorkflowDefinition;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.nosql.Template;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@ApplicationScoped
@Path("workflow")
public class Workflow {

	/**
	 * 
	 */
	transient Template template;
	
	/**
	 * 
	 */
	private NoSQLTemplate<WorkflowDefinition> nosql;
	
	/**
	 * 
	 */
	@PostConstruct
	void postConstruct() {
		nosql = new NoSQLTemplate<>(WorkflowDefinition.class, template);
	}
	
	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(final InputStream body) throws Exception {
		return nosql.insert(body);
	}
	
	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(final InputStream body) throws Exception {
		return nosql.update(body);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") final String id) throws Exception {
		return nosql.find(id);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Path("{id}")
	@DELETE
	public Response delete(@PathParam("id") final String id) throws Exception {
		return nosql.delete(id);
	}
}
