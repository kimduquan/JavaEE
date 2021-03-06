/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.config;

import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;

/**
 *
 * @author FOXCONN
 */
@Path("config")
@RequestScoped
public class Config {
    
    @Inject
    private Request request;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> getConfigurations(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo) throws Exception {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("GET", null);
    }
}
