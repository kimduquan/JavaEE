package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import javax.ws.rs.sse.SseEventSource;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Application.class.getName());
    
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
     * @throws Exception 
     */
    public CompletionStage<Response> request(
    		final String service,
    		final SecurityContext context,
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		final URI serviceUri = registry.lookup(service).orElseThrow(NotFoundException::new);
		final Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serviceUri);
		target = RequestUtil.buildTarget(target, uriInfo, serviceUri);
		Invocation.Builder invoke = target.request();
		final URI baseUri = uriInfo.getBaseUri();
		invoke = RequestUtil.buildHeaders(invoke, headers, baseUri);
		final CompletionStageRxInvoker rx = invoke.rx();
		return RequestUtil.invoke(rx, req.getMethod(), headers.getMediaType(), body)
				.thenApply(res -> RequestUtil.buildResponse(res, baseUri))
				.whenComplete((res, err) -> {
					client.close();
					//res.close();
				})
				.exceptionally(ex -> { 
					LOGGER.log(Level.WARNING, "request", ex);
					return Response.serverError().build(); 
					});
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param sseEventSink
     * @param sse
     * @throws Exception 
     */
    public void stream(
    		final String service,
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final SseEventSink sseEventSink,
            final Sse sse) throws Exception {
    	final Client client = ClientBuilder.newClient();
    	final URI serviceUri = registry.lookup(service).orElseThrow(NotFoundException::new);
    	WebTarget target = client.target(serviceUri);
    	target = RequestUtil.buildTarget(target, uriInfo, serviceUri);
    	final SseEventSource.Builder builder = SseEventSource.target(target);
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
						LOGGER.log(Level.WARNING, "stream", error);
					});
    		source.open();
    	}
    }
}
