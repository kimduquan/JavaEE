/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.net;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.gateway.Request;
import epf.naming.Naming;
import epf.util.StringUtil;
import epf.util.http.SessionUtil;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.NET)
@ApplicationScoped
public class Net {
    
    /**
     * 
     */
    @Inject
    transient Request request;
    
    /**
     * 
     */
    @Inject
    transient ClientUtil clientUtil;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Cache.CACHE_URL)
    String cacheUrl;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     */
    @Path("url")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> rewriteUrl(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    ) throws Exception {
        return request.request(headers, uriInfo, req, body);
    }
    
    /**
     * @param url
     * @param request
     * @return
     * @throws Exception
     */
    @Path("url")
	@GET
    @Asynchronous
    public CompletionStage<Response> temporaryRedirect(
    		@QueryParam("url")
    		final String url,
            @Context 
            final HttpServletRequest request
    ) throws Exception {
        final Optional<Object> attrValue = SessionUtil.getMapAttribute(request, Naming.Net.NET_URL, "urls", url);
        String urlString = "";
        if(attrValue.isEmpty()) {
        	final int id = StringUtil.fromShortString(url);
        	try(Client client = clientUtil.newClient(new URI(cacheUrl))){
        		urlString = client.request(
        				target -> target.path("net").path("url").queryParam("id", String.valueOf(id)), 
        				req -> req)
        		.accept(MediaType.TEXT_PLAIN)
        		.get(String.class);
        	}
        }
        else {
        	urlString = attrValue.get().toString();
        }
        return CompletableFuture.completedFuture(Response.temporaryRedirect(new URI(urlString)).build());
    }
}
