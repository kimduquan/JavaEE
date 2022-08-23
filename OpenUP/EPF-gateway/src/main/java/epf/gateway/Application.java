package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.internal.RequestUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    /**
     * 
     */
    @Inject @Readiness
    transient Registry registry;
    
    /**
     * @param client
     * @param service
     * @param jwt
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     */
    public CompletionStage<Response> buildRequest(
    		final Client client,
    		final String service,
    		final JsonWebToken jwt,
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final javax.ws.rs.core.Request req,
            final InputStream body) {
    	final URI serviceUri = registry.lookup(service).orElseThrow(NotFoundException::new);
		return RequestUtil.buildRequest(client, serviceUri, jwt, headers, uriInfo, req, body);
    }
}
