/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.util.client.ClientQueue;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Request {
	
	/**
	 * 
	 */
	private static final String CLASS_NAME = Request.class.getName();
    
	/**
	 * 
	 */
	private transient javax.ws.rs.core.Request rawRequest;
    /**
     * 
     */
    private transient HttpHeaders headers;
    /**
     * 
     */
    private transient UriInfo uriInfo;
    /**
     * 
     */
    private transient Client client;
    /**
     * 
     */
    private transient URI uri;
    
    /**
     * 
     */
    @Inject 
    private transient ManagedExecutor executor;
    
    /**
     * 
     */
    @Inject
    private transient ClientQueue clients;
    
    /**
     * 
     */
    @Inject
    private transient Registry registry;
    
    /**
     * 
     */
    @Inject
    private transient Logger logger;
    
    /**
     * @param in
     * @return
     */
    public CompletionStage<Response> request(final InputStream body){
    	logger.entering(CLASS_NAME, "request", rawRequest.getMethod());
        return executor.supplyAsync(() -> uri = RequestHelper.buildUri(uriInfo, registry))
                .thenApply(newUri -> client = clients.poll(newUri, b -> b))
                .thenApply(newClient -> RequestHelper.buildTarget(newClient, uriInfo, uri))
                .thenApply(target -> RequestHelper.buildRequest(target, headers))
                .thenApply(request -> RequestHelper.buildMethod(request, this.rawRequest.getMethod(), headers.getMediaType(), body))
                .thenApply(response -> RequestHelper.buildResponse(response, uriInfo))
                .thenApply(ResponseBuilder::build)
                .whenComplete((res, ex) -> {
                	if(ex != null) {
                		logger.throwing(CLASS_NAME, "request", ex);
                	}
                	if(ex instanceof ProcessingException && ex.getCause() instanceof IOException) {
                		client.close();
                	}
                	else {
                        clients.add(uri, client);
                	}
                    logger.exiting(CLASS_NAME, "request");
                });
    }

    public void setHeaders(final HttpHeaders headers) {
        this.headers = headers;
    }

    public void setUriInfo(final UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
    
    /**
     * @param request
     */
    public void setRequest(final javax.ws.rs.core.Request request) {
    	this.rawRequest = request;
    }
}
