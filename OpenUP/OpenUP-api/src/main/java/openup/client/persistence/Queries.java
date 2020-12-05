/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.persistence;

import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface Queries {
    
    @GET
    @Path("query/{criteria: .+}")
    public Response getCriteriaQueryResult(
            @PathParam("criteria")
            List<PathSegment> paths,
            @QueryParam("first")
            Integer firstResult,
            @QueryParam("max")
            Integer maxResults
            ) throws Exception;
    
    @GET
    @Path("queries/{query}")
    Response getNamedQueryResult(
            @PathParam("query")
            String name,
            @MatrixParam("first")
            Integer firstResult,
            @MatrixParam("max")
            Integer maxResults,
            @Context 
            UriInfo uriInfo
            ) throws Exception;
}
