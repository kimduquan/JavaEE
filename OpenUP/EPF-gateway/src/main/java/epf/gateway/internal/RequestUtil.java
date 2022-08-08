package epf.gateway.internal;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.sse.SseEventSource;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public interface RequestUtil {
	
	/**
	 * @param webTarget
	 * @param segment
	 * @return
	 */
	static WebTarget buildMatrixParameters(WebTarget webTarget, final PathSegment segment) {
		final MultivaluedMap<String, String> matrixParams = segment.getMatrixParameters();
        if(matrixParams != null){
        	for(Entry<String, List<String>> entry : matrixParams.entrySet()) {
        		final String key = entry.getKey();
        		if(!Naming.Management.TENANT.equals(key)) {
            		final List<String> value = entry.getValue();
            		if(value == null) {
                		webTarget = webTarget.matrixParam(key);
                	}
                	else {
                    	webTarget = webTarget.matrixParam(key, value.toArray(new Object[0]));
                	}
        		}
        	}
        }
		return webTarget;
	}
	
	/**
	 * @param webTarget
	 * @param uriInfo
	 * @return
	 */
	static WebTarget buildQueryParameters(WebTarget webTarget, final UriInfo uriInfo) {
		final MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();        
        if(queryParams != null){
        	for(Entry<String, List<String>> entry : queryParams.entrySet()) {
        		final String key = entry.getKey();
        		final List<String> value = entry.getValue();
        		if(value == null) {
            		webTarget = webTarget.queryParam(key);
            	}
            	else {
            		webTarget = webTarget.queryParam(key, value.toArray(new Object[0]));
            	}
        	}
        }
        return webTarget;
	}
    
    /**
     * @param webTarget
     * @param uriInfo
     * @param jwt
     * @return
     */
    static WebTarget buildTarget(
    		WebTarget webTarget,
    		final UriInfo uriInfo,
    		final JsonWebToken jwt){
        if(uriInfo != null){
        	final List<PathSegment> segments = uriInfo.getPathSegments();
            if(segments != null){
            	final Iterator<PathSegment> segmentIt = segments.iterator();
            	final PathSegment firstSegemnt = segmentIt.next();
            	getTenantParameter(jwt, firstSegemnt);
            	webTarget = buildMatrixParameters(webTarget, firstSegemnt);
            	while(segmentIt.hasNext()) {
            		final PathSegment segment = segmentIt.next();
            		webTarget = webTarget.path(segment.getPath());
            		webTarget = buildMatrixParameters(webTarget, segment);
            	}
            }
            webTarget = buildQueryParameters(webTarget, uriInfo);
        }
        return webTarget;
    }
    
    /**
     * @param jwt
     * @param firstSegemnt
     * @return
     */
    static Optional<String> getTenantParameter(final JsonWebToken jwt, final PathSegment firstSegemnt) {
    	Optional<String> tenantClaim = Optional.empty();
    	final Optional<String> tenantParam = firstSegemnt.getMatrixParameters() != null ? Optional.ofNullable(firstSegemnt.getMatrixParameters().getFirst(Naming.Management.TENANT)) : Optional.empty();
		if(jwt != null) {
    		tenantClaim = Optional.ofNullable(jwt.getClaim(Naming.Management.TENANT));
		}
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
    
    /**
     * @param input
     * @param headers
     * @param baseUri
     * @return
     */
    static Builder buildHeaders(final Builder input, final HttpHeaders headers, final URI baseUri){
    	Builder builder = input;
        if(headers != null){
            final MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
            final MultivaluedHashMap<String, Object> forwardHeaders = new MultivaluedHashMap<String, Object>(requestHeaders);
            forwardHeaders.remove(HttpHeaders.HOST);
            forwardHeaders.remove(HttpHeaders.HOST.toLowerCase());
            forwardHeaders.remove(HttpHeaders.CONTENT_LENGTH);
            forwardHeaders.remove(HttpHeaders.CONTENT_LENGTH.toLowerCase());
            forwardHeaders.remove(HttpHeaders.AUTHORIZATION);
            forwardHeaders.remove(HttpHeaders.AUTHORIZATION.toLowerCase());
            builder = builder.headers(forwardHeaders);
            List<Locale> languages = headers.getAcceptableLanguages();
            if(languages == null || languages.isEmpty() || languages.get(0).getLanguage().isEmpty()) {
            	languages = Arrays.asList(Locale.getDefault());
                builder = builder.acceptLanguage(languages.toArray(new Locale[0]));
            }
            if(requestHeaders.containsKey(HttpHeaders.AUTHORIZATION)){
            	builder = builder.header(HttpHeaders.AUTHORIZATION, headers.getHeaderString(HttpHeaders.AUTHORIZATION));
            }
            else if(requestHeaders.containsKey(HttpHeaders.AUTHORIZATION.toLowerCase())){
            	builder = builder.header(HttpHeaders.AUTHORIZATION, headers.getHeaderString(HttpHeaders.AUTHORIZATION.toLowerCase()));
            }
            final List<String> forwardedHost = headers.getRequestHeader(Naming.Gateway.Headers.X_FORWARDED_HOST);
            final List<String> forwardedPort = headers.getRequestHeader(Naming.Gateway.Headers.X_FORWARDED_PORT);
            final List<String> forwardedProto = headers.getRequestHeader(Naming.Gateway.Headers.X_FORWARDED_PROTO);
            final List<String> newForwardedHost = new ArrayList<>(forwardedHost);
            newForwardedHost.add(baseUri.getHost());
            final List<String> newForwardedPort = new ArrayList<>(forwardedPort);
            newForwardedPort.add(String.valueOf(baseUri.getPort()));
            final List<String> newForwardedProto = new ArrayList<>(forwardedProto);
            newForwardedProto.add(baseUri.getScheme());
            builder = builder.header(Naming.Gateway.Headers.X_FORWARDED_HOST, StringUtil.valueOf(newForwardedHost, ","));
            builder = builder.header(Naming.Gateway.Headers.X_FORWARDED_PORT, StringUtil.valueOf(newForwardedPort, ","));
            builder = builder.header(Naming.Gateway.Headers.X_FORWARDED_PROTO, StringUtil.valueOf(newForwardedProto, ","));
        }
        return builder;
    }
    
    /**
     * @param invoker
     * @param method
     * @param type
     * @param body
     * @return
     */
    static CompletionStage<Response> invoke(
    		final CompletionStageRxInvoker invoker,
    		final String method, 
    		final MediaType type, 
    		final InputStream body){
    	if(body == null || type == null) {
    		return invoker.method(method);
    	}
    	return invoker.method(method, Entity.entity(body, type));
    }
    
    /**
     * @param builder
     * @return
     */
    static SseEventSource.Builder buildSource(final SseEventSource.Builder builder){
    	return builder;
    }
}
