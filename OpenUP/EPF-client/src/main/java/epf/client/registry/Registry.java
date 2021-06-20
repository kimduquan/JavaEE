package epf.client.registry;

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
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@Path("registry")
public interface Registry {
	
    /**
     * 
     */
    String REGISTRY_URL = "epf.registry.url";
    
	/**
	 * 
	 */
	String REMOTE = "remote";
	
	/**
	 * 
	 */
	String NAME = "name";
	
	/**
	 * 
	 */
	String VERSION = "version";

	/**
	 * @param name
	 * @param remote
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	void bind(@FormParam(NAME) final String name, @FormParam(REMOTE) final URI remote, @QueryParam(VERSION) final String version);
	/**
	 * @param client
	 * @param name
	 * @param remote
	 * @throws Exception
	 */
	static void bind(final Client client, final String name, final URI remote, final String version){
		final Form form = new Form();
		form.param(NAME, name);
		form.param(REMOTE, remote.toString());
		client
		.request(
				target -> target.queryParam(VERSION, version), 
				req -> req
				)
		.post(Entity.form(form));
	}
	
	/**
	 * @return
	 */
	@GET
	Response list(@QueryParam(VERSION) final String version);
	
	/**
	 * @param client
	 * @return
	 */
	static Set<Link> list(final Client client, final String version) {
		return client
				.request(
						target -> version != null ? target.queryParam(VERSION, version) : target, 
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
	Response lookup(@PathParam(NAME) final String name, @QueryParam(VERSION) final String version);
	
	/**
	 * @param client
	 * @param name
	 * @return
	 */
	static Response lookup(final Client client, final String name, final String version){
		return client
				.request(
						target -> target.path(name).queryParam(VERSION, version), 
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
	void rebind(@PathParam(NAME) final String name, @FormParam(REMOTE) final URI remote, @QueryParam(VERSION) final String version);
	
	/**
	 * @param client
	 * @param name
	 * @param remote
	 */
	static void rebind(final Client client, final String name, final URI remote, final String version) {
		final Form form = new Form();
		form.param(REMOTE, remote.toString());
		client
		.request(
				target -> target.path(name).queryParam(VERSION, version), 
				req -> req
				)
		.post(Entity.form(form));
	}
	
	/**
	 * @param name
	 */
	@DELETE
	@Path("{name}")
	void unbind(@PathParam(NAME) final String name, @QueryParam(VERSION) final String version);
	
	/**
	 * @param client
	 * @param name
	 */
	static void unbind(final Client client, final String name, final String version) {
		client
		.request(
				target -> target.path(name).queryParam(VERSION, version), 
				req -> req
				)
		.delete();
	}
}
