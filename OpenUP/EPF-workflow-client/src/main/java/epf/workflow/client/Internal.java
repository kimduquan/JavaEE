package epf.workflow.client;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
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

@Path(Naming.WORKFLOW)
public interface Internal {
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response end(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance) throws Exception;
	
	@DELETE
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	Response terminate(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance) throws Exception;
	
	@PUT
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response end(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version,
			@QueryParam("terminate")
			final Boolean terminate,
			@QueryParam("compensate")
			final Boolean compensate,
			@QueryParam("continueAs")
			final String continueAs,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;

	@POST
	@Path("{workflow}/{nextState}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response transition(
			@PathParam("workflow")
			final String workflow,
			@QueryParam("version")
			final String version,  
			@PathParam("nextState")
			final String nextState,
			@QueryParam("compensate")
			final Boolean compensate,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
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
	
	@POST
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
			final Map<String, Object> map) throws Exception;
	
	@PATCH
	@Path("{workflow}/{state}/events")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response onEvents(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			final List<Map<String, Object>> events) throws Exception;
	
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
			final Map<String, Object> map) throws Exception;
	
	@PATCH
	@Path("{workflow}/{state}/batch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response batch(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
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
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
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
			@QueryParam("at")
			final int at,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception;
	
	@PATCH
	@Path("{workflow}/{state}/{action}/events")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response observes(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@PathParam("action")
			final String action,
			@QueryParam("version")
			final String version,
			final Map<String, Object> map) throws Exception;

	static Link branchLink(final String workflow, final Optional<String> version, final String state, final int at) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/branch");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		uri = uri.queryParam("at", at);
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}
	
	static Link observesLink(final String workflow, final String state, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/events");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.POST, workflow, state);
	}
	
	static Link onEventsLink(final String workflow, final String state, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/events");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}
	
	static Link callbackLink(final String workflow, final String state, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/callback");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}
	
	static Link batchLink(final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/batch");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}

	static Link iterationLink(final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/iteration");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}

	static Link actionLink(final String workflow, final Optional<String> version, final String state, final String action) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/{action}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.POST, workflow, state, action);
	}

	static Link compensateLink(final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
	}

	static Link transitionLink(final String workflow, final Optional<String> version, final String nextState, final Boolean compensate) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{nextState}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		if(compensate != null) {
			uri = uri.queryParam("compensate", compensate);
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.POST, workflow, nextState);
	}

	static Link terminateLink() {
		UriBuilder uri = UriBuilder.fromUri("");
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.DELETE);
	}

	static Link endLink(final String workflow, final Optional<String> version, final Boolean terminate, final Boolean compensate, final String continueAs) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		if(terminate != null) {
			uri = uri.queryParam("terminate", terminate);
		}
		if(compensate != null) {
			uri = uri.queryParam("compensate", compensate);
		}
		if(continueAs != null) {
			uri = uri.queryParam("continueAs", continueAs);
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PUT, workflow);
	}
}
