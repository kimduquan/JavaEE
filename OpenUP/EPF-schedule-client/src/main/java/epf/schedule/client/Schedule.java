package epf.schedule.client;

import java.io.InputStream;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.client.util.LinkUtil;
import epf.naming.Naming;

@Path(Naming.SCHEDULE)
public interface Schedule {

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
	
	static Link scheduleLink(
			final String service, 
			final String method,
			final String path,
			final String recurringTimeInterval) {
		return LinkUtil.build(Naming.SCHEDULE, HttpMethod.POST, "REST?service=" + service + ";method=" + method + ";recurringTimeInterval=" + recurringTimeInterval + path);
	}
	
	@Path("REST")
	@DELETE
	Response cancel(
			@Context 
			final UriInfo uriInfo,
			@PathParam("paths")
			final List<PathSegment> paths);
	
	static Response cancel(
			final Client client, 
			final String identityName) {
		return client.request(
				target -> target.path("REST").matrixParam("identityName", identityName), 
				req -> req
				).delete();
	}
}
