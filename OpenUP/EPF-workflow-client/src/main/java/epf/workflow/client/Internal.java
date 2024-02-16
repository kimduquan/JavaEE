package epf.workflow.client;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.naming.Naming;
import epf.workflow.client.util.LinkUtil;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

/**
 * 
 */
@Path(Naming.WORKFLOW)
public interface Internal {
	
	/**
	 * @param instance
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response end(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance) throws Exception;
	
	/**
	 * @param instance
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	Response terminate(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance) throws Exception;
	
	/**
	 * @param workflow
	 * @param version
	 * @param instance
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response end(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version, 
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;

	/**
	 * @param workflow
	 * @param state
	 * @param version
	 * @param instance
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{workflow}/{state}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response transition(
			@PathParam("workflow")
			final String workflow,  
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
	/**
	 * @param workflow
	 * @param state
	 * @param version
	 * @param instance
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{workflow}/{state}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response compensate(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state, 
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
	/**
	 * @param workflow
	 * @param state
	 * @param action
	 * @param version
	 * @param instance
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{workflow}/{state}/{action}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response action(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@PathParam("action")
			final String action,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
	/**
	 * @param workflow
	 * @param state
	 * @param version
	 * @param instance
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@PATCH
	@Path("{workflow}/{state}/events")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response observes(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final Map<String, Object> map) throws Exception;
	
	/**
	 * @param workflow
	 * @param state
	 * @param version
	 * @param instance
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@PATCH
	@Path("{workflow}/{state}/callback")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response callback(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final Map<String, Object> map) throws Exception;
	
	/**
	 * @param workflow
	 * @param state
	 * @param version
	 * @param next
	 * @param instance
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PATCH
	@Path("{workflow}/{state}/iteration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response iteration(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@QueryParam("next")
			final int next,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
	/**
	 * @param workflow
	 * @param state
	 * @param version
	 * @param index
	 * @param instance
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PATCH
	@Path("{workflow}/{state}/branch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response branch(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@QueryParam("index")
			final int index,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;

	/**
	 * @param index
	 * @param _synchronized
	 * @param workflow
	 * @param version
	 * @param state
	 * @param branchIndex
	 * @return
	 */
	static Link branchLink(final int index, final boolean _synchronized, final String workflow, final Optional<String> version, final String state, final int branchIndex) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/branch");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		uri = uri.queryParam("index", branchIndex);
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param state
	 * @param version
	 * @return
	 */
	static Link observesLink(final int index, final String workflow, final String state, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/events");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}
	
	/**
	 * @param index
	 * @param workflow
	 * @param state
	 * @param version
	 * @return
	 */
	static Link callbackLink(final int index, final String workflow, final String state, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/callback");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}

	/**
	 * @param index
	 * @param _synchronized
	 * @param workflow
	 * @param version
	 * @param state
	 * @param next
	 * @return
	 */
	static Link iterationLink(final int index, final boolean _synchronized, final String workflow, final Optional<String> version, final String state, final int next) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/iteration");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		uri = uri.queryParam("next", next);
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}

	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @param state
	 * @param action
	 * @return
	 */
	static Link actionLink(final int index, final String workflow, final Optional<String> version, final String state, final String action) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/{action}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.POST, workflow, state, action);
	}

	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link compensateLink(final int index, final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
	}

	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link transitionLink(final int index, final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.POST, workflow, state);
	}

	/**
	 * @param index
	 * @return
	 */
	static Link terminateLink(final int index) {
		UriBuilder uri = UriBuilder.fromUri("");
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.DELETE);
	}

	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link endLink(final int index, final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PUT, workflow);
	}
}
