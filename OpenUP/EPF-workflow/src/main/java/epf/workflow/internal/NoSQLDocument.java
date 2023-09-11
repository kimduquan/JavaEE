package epf.workflow.internal;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * 
 */
@RegisterRestClient
@Path(Naming.WORKFLOW)
public interface NoSQLDocument {
	
	/**
	 * 
	 */
	String DOCUMENT = "document";
	
	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@Path(DOCUMENT)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insert(final Object object) throws Exception;
	
	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@Path(DOCUMENT)
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response update(final Object object) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Path("document/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response find(@PathParam("id") final String id) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Path("document/{id}")
	@DELETE
	Response delete(@PathParam("id") final String id) throws Exception;
}
