package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.internal.RequestUtil;
import epf.gateway.internal.ResponseUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
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
		target = RequestUtil.buildTarget(target, uriInfo, context);
		Invocation.Builder invoke = target.request();
		final URI baseUri = uriInfo.getBaseUri();
		invoke = RequestUtil.buildHeaders(invoke, headers, baseUri);
		final CompletionStageRxInvoker rx = invoke.rx();
		return RequestUtil.invoke(rx, req.getMethod(), headers.getMediaType(), body)
				.thenApply(res -> ResponseUtil.buildResponse(res, baseUri))
				.whenComplete((res, err) -> ResponseUtil.closeResponse(client, res, err));
    }
}
