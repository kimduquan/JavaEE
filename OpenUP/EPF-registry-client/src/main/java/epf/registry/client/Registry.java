package epf.registry.client;

import java.net.URI;
import java.util.Set;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.REGISTRY)
public interface Registry {

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	void bind(
			@FormParam(Naming.Registry.Client.NAME) 
			final String name, 
			@FormParam(Naming.Registry.Client.REMOTE) 
			final URI remote, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version);
	
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
	
	@GET
	Response list(
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version, 
			@Context 
			final UriInfo uriInfo);
	
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
	
	@GET
	@Path("{name}")
	Response lookup(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version);
	
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
	
	@DELETE
	@Path("{name}")
	void unbind(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version);
	
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
