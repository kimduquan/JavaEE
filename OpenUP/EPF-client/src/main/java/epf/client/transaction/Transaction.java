package epf.client.transaction;

import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import epf.client.Request;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.TRANSACTION)
public interface Transaction {
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @param requests
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	CompletionStage<Response> commit(
			@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request request,
            final List<Request> requests) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	@DELETE
	Response rollback() throws Exception;
}
