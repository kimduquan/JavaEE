/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import openup.Roles;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("persistence/query")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
public class QueryManager {
    
    @Inject
    private Cache cache;
    
    @Context
    private SecurityContext context;
    
    @GET
    @Operation(
            summary = "Native Query", 
            description = "Execute a SELECT query and return the query results."
    )
    @APIResponse(
            description = "Result",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    public Response query(
            @MatrixParam("first")
            Integer firstResult,
            @MatrixParam("max")
            Integer maxResults,
            @Context 
            UriInfo uriInfo
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        return response.build();
    }
    
    @GET
    @Path("{query}")
    @Operation(
            summary = "Named Query", 
            description = "Execute a SELECT query and return the query results."
    )
    @APIResponse(
            description = "Result",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    @APIResponse(
            description = "a query has not been defined with the given name",
            responseCode = "404"
    )
    public Response getNamedQueryResult(
            @PathParam("query")
            String name,
            @MatrixParam("first")
            Integer firstResult,
            @MatrixParam("max")
            Integer maxResults,
            @Context 
            UriInfo uriInfo
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Query query = null;
        try{
            query = cache.createNamedQuery(context.getUserPrincipal(), name);
        }
        catch(IllegalArgumentException ex){
            response.status(Response.Status.NOT_FOUND);
        }
        if(query != null){
            buildQuery(query, firstResult, maxResults, uriInfo);
            response.entity(
                    query.getResultStream()
                            .collect(Collectors.toList()));
        }
        return response.build();
    }
    
    void buildQuery(Query query, Integer firstResult, Integer maxResults, UriInfo uriInfo){
        uriInfo.getQueryParameters().forEach((param, paramValue) -> {
            String value = "";
            if(!paramValue.isEmpty()){
                value = paramValue.get(0);
            }
            query.setParameter(param, value);
        });
        if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
    }
}
