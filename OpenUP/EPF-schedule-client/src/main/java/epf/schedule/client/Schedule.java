package epf.schedule.client;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.SCHEDULE)
public interface Schedule {

	/**
	 * @param uriInfo
	 * @param headers
	 * @param paths
	 * @param body
	 * @return
	 */
	@Path("REST/{paths: .+}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	Response schedule(
			@Context 
			final UriInfo uriInfo,
			@Context 
			final HttpHeaders headers,
			@PathParam("paths")
			final List<PathSegment> paths,
			final InputStream body);
	
	/**
	 * @param client
	 * @param method
	 * @param path
	 * @param recurringTimeInterval
	 * @param body
	 * @return
	 */
	static Response schedule(
			final Client client, 
			final String service,
			final String method,
			final String path,
			final String recurringTimeInterval,
			final InputStream body) {
		return client.request(
				target -> target.path("REST").matrixParam("service", service).matrixParam("method", method).matrixParam("recurringTimeInterval", recurringTimeInterval).path(path), 
				req -> req
				).post(Entity.entity(body, MediaType.APPLICATION_JSON));
	}
	
	/**
	 * @param uriInfo
	 * @param paths
	 * @return
	 */
	@Path("REST")
	@DELETE
	Response cancel(
			@Context 
			final UriInfo uriInfo,
			@PathParam("paths")
			final List<PathSegment> paths);
	
	/**
	 * @param client
	 * @param identityName
	 * @return
	 */
	static Response cancel(
			final Client client, 
			final String identityName) {
		return client.request(
				target -> target.path("REST").matrixParam("identityName", identityName), 
				req -> req
				).delete();
	}
}
