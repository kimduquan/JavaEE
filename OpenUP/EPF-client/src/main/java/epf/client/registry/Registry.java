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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.util.client.Client;

@Path("registry")
public interface Registry {

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	void bind(@FormParam("name") String name, @FormParam("remote") URI remote) throws Exception;
	static void bind(Client client, String name,  URI remote) throws Exception{
		Form form = new Form();
		form.param("name", name);
		form.param("remote", remote.toString());
		client
		.request(
				target -> target, 
				req -> req
				)
		.post(Entity.form(form));
	}
	
	@GET
	Response list() throws Exception;
	static Set<Link> list(Client client) throws Exception{
		return client
				.request(
						target -> target, 
						req -> req
						)
				.get()
				.getLinks();
	}
	
	@GET
	@Path("{name}")
	Response lookup(@PathParam("name") String name) throws Exception;
	static Response lookup(Client client, String name) throws Exception{
		return client
				.request(
						target -> target.path(name), 
						req -> req
						)
				.get();
	}
	
	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	void rebind(@PathParam("name") String name, @FormParam("remote") URI remote) throws Exception;
	static void rebind(Client client, String name, URI remote) throws Exception{
		Form form = new Form();
		form.param("remote", remote.toString());
		client
		.request(
				target -> target.path(name), 
				req -> req
				)
		.post(Entity.form(form));
	}
	
	@DELETE
	@Path("{name}")
	void unbind(@PathParam("name") String name) throws Exception;
	static void unbind(Client client, String name) throws Exception{
		client
		.request(
				target -> target.path(name), 
				req -> req
				)
		.delete();
	}
}
