package epf.gateway.internal;

import java.io.InputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.util.RequestUtil;
import epf.naming.Naming;

/**
 * 
 */
public class RequestBuilder {
	
	/**
	 *
	 */
	private transient final Client client;
	
	/**
	 *
	 */
	private final URI serviceUrl;
	/**
	 *
	 */
	private transient final HttpHeaders headers;
	/**
	 *
	 */
	private transient final UriInfo uriInfo;
	/**
	 *
	 */
	private transient final Request request;
	/**
	 *
	 */
	private transient final InputStream body;
	
	/**
	 * @param client
	 * @param serviceUrl
	 * @param request
	 * @param headers
	 * @param uriInfo
	 * @param body
	 */
	public RequestBuilder(final Client client, final URI serviceUrl, final Request request, final HttpHeaders headers, final UriInfo uriInfo, final InputStream body) {
		this.client = client;
		this.serviceUrl = serviceUrl;
		this.headers = headers;
		this.uriInfo = uriInfo;
		this.request = request;
		this.body = body;
		
	}
	
	/**
	 * @return
	 */
	public CompletionStage<Response> build(){
		return RequestUtil.buildRequest(client, serviceUrl, headers, uriInfo, request, body);
	}
    
    /**
     * @param jwt
     * @param uriInfo
     * @return
     */
    public static Optional<String> getTenantParameter(final JsonWebToken jwt, final UriInfo uriInfo) {
		Optional<String> tenantClaim = Optional.empty();
    	if(jwt != null) {
    		tenantClaim = Optional.ofNullable(jwt.getClaim(Naming.Management.TENANT));
		}
    	if(uriInfo != null) {
    		final List<PathSegment> segments = uriInfo.getPathSegments();
    		if(segments != null) {
    			final Iterator<PathSegment> segmentIt = segments.iterator();
    			if(segmentIt.hasNext()) {
    				final PathSegment firstSegemnt = segmentIt.next();
    		    	final Optional<String> tenantParam = firstSegemnt.getMatrixParameters() != null ? Optional.ofNullable(firstSegemnt.getMatrixParameters().getFirst(Naming.Management.TENANT)) : Optional.empty();
    		    	if(tenantClaim.isPresent()) {
    					if(!tenantParam.isPresent()) {
    						return tenantClaim;
    					}
    					else if(!tenantClaim.get().equals(tenantParam.get())) {
    						throw new ForbiddenException();
    					}
    				}
    				return tenantParam;
    			}
    		}
    	}
    	return tenantClaim;
    }
}
