/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.config;

import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import openup.gateway.Request;
import org.eclipse.microprofile.faulttolerance.Asynchronous;

/**
 *
 * @author FOXCONN
 */
@Path("config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Config {
    
    @Inject
    private Request request;
    
    @GET
    @Asynchronous
    public CompletionStage<Response> getConfigurations(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo) throws Exception {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("GET", null);
    }
}
