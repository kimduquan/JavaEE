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
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Registry;
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
    transient Registry registry;
    
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
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    ) throws Exception {
        return request.request(context, headers, uriInfo, req, body);
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
        if(attrValue.isEmpty()) {
        	final int id = StringUtil.fromShortString(url);
        	final URI cacheUrl = registry.lookup(Naming.CACHE);
        	return ClientBuilder.newClient().target(cacheUrl).path(Naming.NET).path("url").queryParam("id", String.valueOf(id)).request(MediaType.TEXT_PLAIN_TYPE).rx().get(String.class)
        	.thenApply(urlString -> {
        		try {
					return Response.temporaryRedirect(new URI(urlString)).build();
				} 
        		catch (Exception e) {
					return Response.serverError().build();
				}
        	});
        }
        return CompletableFuture.completedFuture(Response.temporaryRedirect(new URI(attrValue.get().toString())).build());
    }
}
