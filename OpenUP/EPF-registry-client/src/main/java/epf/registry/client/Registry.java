package epf.registry.client;

import java.net.URI;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.REGISTRY)
public interface Registry {

	/**
	 * @param name
	 * @param remote
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	void bind(
			@FormParam(Naming.Registry.Client.NAME) 
			final String name, 
			@FormParam(Naming.Registry.Client.REMOTE) 
			final URI remote, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version);
	/**
	 * @param client
	 * @param name
	 * @param remote
	 * @throws Exception
	 */
	static void bind(
			final Client client, 
			final String name, 
			final URI remote, 
			final String version){
		final Form form = new Form();
		form.param(Naming.Registry.Client.NAME, name);
		form.param(Naming.Registry.Client.REMOTE, remote.toString());
		client
		.request(
				target -> target.queryParam(Naming.Registry.Client.VERSION, version), 
				req -> req
				)
		.post(Entity.form(form));
	}
	
	/**
	 * @return
	 */
	@GET
	Response list(
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version, 
			@Context 
			final UriInfo uriInfo);
	
	/**
	 * @param client
	 * @return
	 */
	static Set<Link> list(
			final Client client, 
			final String version) {
		return client
				.request(
						target -> version != null ? target.queryParam(Naming.Registry.Client.VERSION, version) : target, 
						req -> req
						)
				.get()
				.getLinks();
	}
	
	/**
	 * @param name
	 * @return
	 */
	@GET
	@Path("{name}")
	Response lookup(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version);
	
	/**
	 * @param client
	 * @param name
	 * @return
	 */
	static Response lookup(
			final Client client, 
			final String name, 
			final String version){
		return client
				.request(
						target -> target.path(name).queryParam(Naming.Registry.Client.VERSION, version), 
						req -> req
						)
				.get();
	}
	
	/**
	 * @param name
	 * @param remote
	 */
	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	void rebind(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@FormParam(Naming.Registry.Client.REMOTE) 
			final URI remote, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version);
	
	/**
	 * @param client
	 * @param name
	 * @param remote
	 */
	static void rebind(
			final Client client, 
			final String name, 
			final URI remote, 
			final String version) {
		final Form form = new Form();
		form.param(Naming.Registry.Client.REMOTE, remote.toString());
		client
		.request(
				target -> target.path(name).queryParam(Naming.Registry.Client.VERSION, version), 
				req -> req
				)
		.post(Entity.form(form));
	}
	
	/**
	 * @param name
	 */
	@DELETE
	@Path("{name}")
	void unbind(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version);
	
	/**
	 * @param client
	 * @param name
	 */
	static void unbind(
			final Client client, 
			final String name, 
			final String version) {
		client
		.request(
				target -> target.path(name).queryParam(Naming.Registry.Client.VERSION, version), 
				req -> req
				)
		.delete();
	}
}
