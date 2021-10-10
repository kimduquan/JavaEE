/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import javax.ws.rs.sse.SseEventSource;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.client.util.ssl.DefaultHostnameVerifier;
import epf.client.util.ssl.SSLContextHelper;
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
    private transient ManagedExecutor executor;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @throws Exception 
     */
    public CompletionStage<Response> request(
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		URI uri = RequestUtil.buildUri(uriInfo, registry);
		/*ClientBuilder builder = ClientBuilder.newBuilder()
		.executorService(executor)
		.hostnameVerifier(new DefaultHostnameVerifier())
		.sslContext(SSLContextHelper.build());
		Client client = builder.build();
		WebTarget target = client.target(uri);
		target = RequestUtil.buildTarget(target, uriInfo, uri);
		Invocation.Builder invoke = target.request();
		invoke = RequestUtil.buildRequest(invoke, headers);
		CompletionStageRxInvoker rx = invoke.rx();
		CompletionStage<Response> response = RequestUtil.invoke(rx, req.getMethod(), headers.getMediaType(), body);
		return response.thenApply(res -> RequestUtil.buildResponse(res, uriInfo)).thenApply(ResponseBuilder::build);*/
    	return executor.supplyAsync(ClientBuilder::newBuilder)
    	.thenApply(builder -> builder.executorService(executor))
    	.thenApply(builder -> builder.hostnameVerifier(new DefaultHostnameVerifier()))
    	.thenApply(builder -> builder.sslContext(SSLContextHelper.build()))
    	.thenApply(ClientBuilder::build)
    	.thenApply(client -> client.target(uri))
    	.thenApply(target -> RequestUtil.buildTarget(target, uriInfo, uri))
    	.thenApply(WebTarget::request)
    	.thenApply(invoke -> RequestUtil.buildRequest(invoke, headers))
    	.thenApply(invoke -> invoke.rx())
    	.thenCompose(rx -> RequestUtil.invoke(rx, req.getMethod(), headers.getMediaType(), body))
    	.thenApply(res -> RequestUtil.buildResponse(res, uriInfo))
    	.thenApply(ResponseBuilder::build);
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
    	ClientBuilder clientBuilder = ClientBuilder.newBuilder()
    			.executorService(executor)
    			.hostnameVerifier(new DefaultHostnameVerifier())
    			.sslContext(SSLContextHelper.build());
    	Client client = clientBuilder.build();
    	URI uri = RequestUtil.buildUri(uriInfo, registry);
    	WebTarget target = client.target(uri);
    	target = RequestUtil.buildTarget(target, uriInfo, uri);
    	SseEventSource.Builder builder = SseEventSource.target(target);
    	try(SseEventSource source = builder.build()){
    		final OutboundSseEvent.Builder eventBuilder = sse.newEventBuilder();
    		source.register(
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
    		source.open();
    	}
    }
}
