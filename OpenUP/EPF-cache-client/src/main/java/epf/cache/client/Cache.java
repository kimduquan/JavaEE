package epf.cache.client;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.CACHE)
public interface Cache {

	/**
	 * @param tenant
	 * @param name
	 * @param paths
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{name}/{paths: .+}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response put(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("name")
			final String name,
			@PathParam("paths")
			final List<PathSegment> paths,
			final InputStream value
			) throws Exception;
	
	/**
	 * @param client
	 * @param name
	 * @param key
	 * @param value
	 * @return
	 */
	static Response put(final Client client, final String name, final String[] key, final Object value) {
		return client
				.request(
						target -> {
							target = target.path(name);
							for(String k : key) {
								target = target.path(k);
							}
							return target; 
							}, 
						req -> req)
				.post(Entity.entity(value, MediaType.APPLICATION_JSON));
	}
	
	/**
	 * @param tenant
	 * @param name
	 * @param paths
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@PUT
    @Path("{name}/{paths: .+}")
	@Consumes(MediaType.APPLICATION_JSON)
    Response replace(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("name")
			final String name,
    		@PathParam("paths")
    		final List<PathSegment> paths,
			final InputStream value
    		) throws Exception;
	
	/**
	 * @param client
	 * @param name
	 * @param key
	 * @param value
	 * @return
	 */
	static Response replace(final Client client, final String name, final String[] key, final Object value) {
    	return client
				.request(
						target -> {
							target = target.path(name);
							for(String k : key) {
								target = target.path(k);
							}
							return target; 
							}, 
						req -> req)
				.put(Entity.entity(value, MediaType.APPLICATION_JSON));
    }
	
	/**
	 * @param tenant
	 * @param name
	 * @param paths
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("{name}/{paths: .+}")
	Response remove(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("name")
			final String name,
    		@PathParam("paths")
    		final List<PathSegment> paths
    		) throws Exception;
	
	/**
	 * @param client
	 * @param name
	 * @param key
	 * @return
	 */
	static Response remove(final Client client, final String name, final String[] key) {
		return client
				.request(
						target -> {
							target = target.path(name);
							for(String k : key) {
								target = target.path(k);
							}
							return target; 
							}, 
						req -> req)
				.delete();
	}
	
	/**
	 * @param tenant
	 * @param name
	 * @param paths
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("{name}/{paths: .+}")
	@Produces(MediaType.APPLICATION_JSON)
	Response get(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("name")
			final String name,
    		@PathParam("paths")
    		final List<PathSegment> paths
    		) throws Exception;
	
	/**
	 * @param client
	 * @param name
	 * @param key
	 * @return
	 */
	static Response get(final Client client, final String name, final String[] key) {
		return client
				.request(
						target -> {
							target = target.path(name);
							for(String k : key) {
								target = target.path(k);
							}
							return target; 
							}, 
						req -> req)
				.get();
	}
	
	/**
	 * @param tenant
	 * @param name
	 * @param paths
	 * @return
	 * @throws Exception
	 */
	@HEAD
	@Path("{name}/{paths: .+}")
	Response containsKey(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("name")
			final String name,
    		@PathParam("paths")
    		final List<PathSegment> paths
    		) throws Exception;
	
	/**
	 * @param client
	 * @param name
	 * @param key
	 * @return
	 */
	static Response containsKeỵ̣(final Client client, final String name, final String[] key) {
		return client
				.request(
						target -> {
							target = target.path(name);
							for(String k : key) {
								target = target.path(k);
							}
							return target; 
							}, 
						req -> req)
				.head();
	}
	
	/**
	 * @param tenant
	 * @param name
	 * @param map
	 * @return
	 */
	@PATCH
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getAll(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("name")
			final String name,
			final InputStream map);
	
	/**
	 * @param client
	 * @param name
	 * @param keys
	 * @return
	 */
	static Response getAll(final Client client, final String name, final String[] keys) {
		return client
				.request(
						target -> target.path(name), 
						req -> req)
				.method(HttpMethod.PATCH, Entity.entity(keys, MediaType.APPLICATION_JSON));
	}
}