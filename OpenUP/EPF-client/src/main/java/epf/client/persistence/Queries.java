package epf.client.persistence;

import java.util.List;
import java.util.function.Function;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
public interface Queries {
	
	/**
	 * 
	 */
	String FIRST = "first";
	/**
	 * 
	 */
	String MAX = "max";
    
    /**
     * @param schema
     * @param paths
     * @param firstResult
     * @param maxResults
     * @return
     */
    @GET
    @Path("{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
    Response executeQuery(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @QueryParam(FIRST)
            final Integer firstResult,
            @QueryParam(MAX)
            final Integer maxResults,
            @Context
            final SecurityContext context
            );
    
    /**
     * @param client
     * @param schema
     * @param type
     * @param paths
     * @param firstResult
     * @param maxResults
     */
    static <T extends Object> List<T> executeQuery(
    		final Client client,
    		final String schema,
    		final GenericType<List<T>> type,
    		final Function<WebTarget, WebTarget> paths,
    		final Integer firstResult,
    		final Integer maxResults
            ) {
    	return client.request(
    			target -> paths.apply(
    					target.path(schema).queryParam(FIRST, firstResult).queryParam(MAX, maxResults)
    					), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(type);
    }
    
    /**
     * @param client
     * @param schema
     * @param paths
     * @param firstResult
     * @param maxResults
     */
    static Response executeQuery(
    		final Client client,
    		final String schema,
    		final Function<WebTarget, WebTarget> paths,
    		final Integer firstResult,
    		final Integer maxResults){
    	return client.request(
    			target -> paths.apply(
    					target.path(schema).queryParam(FIRST, firstResult).queryParam(MAX, maxResults)
    					), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    }
    
    /**
     * @param uriInfo
     * @param text
     * @param firstResult
     * @param maxResults
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response search(
    		@Context
    		final UriInfo uriInfo,
    		@QueryParam("text")
    		@NotBlank
    		@NotEmpty
    		@Pattern(regexp = "\\w+")
    		final String text, 
    		@QueryParam(FIRST)
    		final Integer firstResult,
            @QueryParam(MAX)
    		final Integer maxResults,
            @Context
            final SecurityContext context);
    
    /**
     * @param client
     * @param text
     * @param firstResult
     * @param maxResults
     * @return
     */
    static Response search(
    		final Client client,
    		final String text, 
    		final Integer firstResult,
    		final Integer maxResults) {
    	return client.request(
    			target -> target.queryParam("text", text).queryParam(FIRST, firstResult).queryParam(MAX, maxResults), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    }
}
