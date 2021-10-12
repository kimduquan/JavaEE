/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.persistence;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@ApplicationScoped
public class Entities {
    
    /**
     * 
     */
    @Inject
    private transient Request request;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param schema
     * @param entity
     * @param body
     */
    @POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> persist(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            final InputStream body) throws Exception {
        return request.request(headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     */
    @PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> merge(
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId,
            final InputStream body
            ) throws Exception {
    	return request.request(headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param schema
     * @param entity
     * @param entityId
     */
    @DELETE
    @Path("{schema}/{entity}/{id}")
    @Asynchronous
    public CompletionStage<Response> remove(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId) throws Exception {
        return request.request(headers, uriInfo, req, null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     */
    @POST
    @Path("{schema}/{entity}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> find(
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId,
            final InputStream body
            ) throws Exception {
    	return request.request(headers, uriInfo, req, body);
    }
}
