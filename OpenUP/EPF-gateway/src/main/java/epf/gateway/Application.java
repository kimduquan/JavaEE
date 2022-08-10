package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
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
     * @param service
     * @param jwt
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     */
    public Invocation buildRequest(
    		final String service,
    		final JsonWebToken jwt,
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final javax.ws.rs.core.Request req,
            final InputStream body) {
    	final URI serviceUri = registry.lookup(service).orElseThrow(NotFoundException::new);
		final Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serviceUri);
		target = RequestUtil.buildTarget(target, uriInfo, jwt);
		Invocation.Builder invoke = target.request();
		final URI baseUri = uriInfo.getBaseUri();
		invoke = RequestUtil.buildHeaders(invoke, headers, baseUri);
		return RequestUtil.buildInvoke(invoke, req.getMethod(), headers.getMediaType(), body);
    }
}
