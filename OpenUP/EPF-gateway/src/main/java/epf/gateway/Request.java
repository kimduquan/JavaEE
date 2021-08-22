/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import javax.ws.rs.sse.SseEventSource;
import epf.util.client.Client;
import epf.util.client.ClientUtil;
import epf.util.logging.Logging;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Request {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Request.class.getName());
    
    /**
     * 
     */
    @Inject
    private transient Registry registry;
    
    /**
     * 
     */
    @Inject
    private ClientUtil clientUtil;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @throws Exception 
     */
    public Response request(
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	LOGGER.entering(getClass().getName(), "request", req.getMethod());
    	final URI uri = RequestUtil.buildUri(uriInfo, registry);
    	try(Client client = clientUtil.newClient(uri)){
    		final Builder newBuilder = client.request(
    				target -> RequestUtil.buildTarget(target, uriInfo, uri), 
    				builder -> RequestUtil.buildRequest(builder, headers)
    				);
    		Response response = RequestUtil.buildMethod(newBuilder, req.getMethod(), headers.getMediaType(), body);
    		response = RequestUtil.buildResponse(response, uriInfo).build();
    		LOGGER.exiting(getClass().getName(), "request");
    		return response;
    	}
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param sseEventSink
     * @param sse
     * @throws Exception 
     */
    public void stream(
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final SseEventSink sseEventSink,
            final Sse sse) throws Exception {
    	final OutboundSseEvent.Builder eventBuilder = sse.newEventBuilder();
    	final URI uri = RequestUtil.buildUri(uriInfo, registry);
    	try(Client client = clientUtil.newClient(uri)){
    		try(SseEventSource stream = client.stream(
    				target -> RequestUtil.buildTarget(target, uriInfo, uri), 
    				b -> RequestUtil.buildSource(b)
    				)){
    			stream.register(
    					e -> {
    						final OutboundSseEvent newEvent = eventBuilder
    								.comment(e.getComment())
		    						.data(e.readData())
				    				.id(e.getId())
				    				.mediaType(MediaType.APPLICATION_JSON_TYPE)
				    				.name(e.getName())
				    				.reconnectDelay(e.getReconnectDelay())
				    				.build();
		    				sseEventSink.send(newEvent);
		    				}
    					,
    					error -> {
    						LOGGER.throwing(getClass().getName(), "stream", error);
    					});
    			stream.open();
    		}
    	}
		sseEventSink.close();
    }
}
