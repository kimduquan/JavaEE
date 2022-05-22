package epf.client.transaction;

import java.io.InputStream;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.TRANSACTION)
public interface Transaction {

	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PATCH
	Response commit(
			@Context 
			final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final Request req,
            final InputStream body) throws Exception;
	
	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param body
	 * @return
	 * @throws Exception
	 */
	Response rollback(
			@Context 
			final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final Request req,
            final InputStream body) throws Exception;
}
