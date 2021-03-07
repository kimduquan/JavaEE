package epf.gateway.registry;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;

@Path("registry")
@RequestScoped
public class Registry {

	@Inject
    private Request request;
    
    @POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Asynchronous
    public CompletionStage<Response> bind(
    		@Context 
    		HttpHeaders headers, 
            @Context 
            UriInfo uriInfo,
            InputStream in
    		) throws Exception{
    	request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("registry", HttpMethod.POST, in);
    }
	
	@GET
	@Asynchronous
    public CompletionStage<Response> list(
    		@Context 
    		HttpHeaders headers, 
            @Context 
            UriInfo uriInfo,
            InputStream in
    		) throws Exception{
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("registry", HttpMethod.GET, null);
	}
	
	@GET
	@Path("{name}")
	@Asynchronous
    public CompletionStage<Response> lookup(
    		@PathParam("name") 
    		String name,
    		@Context 
    		HttpHeaders headers, 
            @Context 
            UriInfo uriInfo,
            InputStream in
    		) throws Exception{
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("registry", HttpMethod.GET, null);
	}
	
	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Asynchronous
    public CompletionStage<Response> rebind(
    		@PathParam("name") 
    		String name, 
    		@Context 
    		HttpHeaders headers, 
            @Context 
            UriInfo uriInfo,
            InputStream in
    		) throws Exception{
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("registry", HttpMethod.PUT, in);
	}
	
	@DELETE
	@Path("{name}")
	@Asynchronous
    public CompletionStage<Response> unbind(
    		@PathParam("name") 
    		String name,
    		@Context 
    		HttpHeaders headers, 
            @Context 
            UriInfo uriInfo,
            InputStream in
    		) throws Exception{
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("registry", HttpMethod.DELETE, null);
	}
}
