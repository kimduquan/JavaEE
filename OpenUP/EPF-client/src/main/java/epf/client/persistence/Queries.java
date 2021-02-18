/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.persistence;

import java.util.List;
import java.util.function.Function;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import epf.util.client.Client;
import epf.validation.persistence.Unit;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
public interface Queries {
    
    @GET
    @Path("{unit}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCriteriaQueryResult(
            @PathParam("unit")
            @Unit
            String unit,
            @PathParam("criteria")
            List<PathSegment> paths,
            @QueryParam("first")
            Integer firstResult,
            @QueryParam("max")
            Integer maxResults
            ) throws Exception;
    
    static <T> List<T> getCriteriaQueryResult(
            Client client,
            GenericType<List<T>> type,
            String unit,
            Function<WebTarget, WebTarget> paths,
            Integer firstResult,
            Integer maxResults
            ) throws Exception{
    	return client.request(
    			target -> paths.apply(
    					target.path(unit).queryParam("first", firstResult).queryParam("max", maxResults)
    					), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(type);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Target> search(
    		@QueryParam("text") 
    		String text, 
    		@QueryParam("first")
            Integer firstResult,
            @QueryParam("max")
            Integer maxResults) throws Exception;
    
    static List<Target> search(
    		Client client,
    		String text, 
    		Integer firstResult,
            Integer maxResults) throws Exception{
    	return client.request(
    			target -> target.queryParam("text", text).queryParam("first", firstResult).queryParam("max", maxResults), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<List<Target>>() {});
    }
}
