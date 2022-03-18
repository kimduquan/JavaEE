package epf.gateway.internal;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.sse.SseEventSource;
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
        		final List<String> value = entry.getValue();
        		if(value == null) {
            		webTarget = webTarget.matrixParam(key);
            	}
            	else {
                	webTarget = webTarget.matrixParam(key, value.toArray(new Object[0]));
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
	 * @param uriInfo
	 * @return
	 */
	static Optional<String> getTenantParameter(final UriInfo uriInfo) {
		Optional<String> tenant = Optional.empty();
		final List<PathSegment> segments = uriInfo.getPathSegments();
		if(segments != null && segments.size() > 0) {
			final PathSegment firstSegment = segments.get(0);
			final MultivaluedMap<String, String> matrixParams = firstSegment.getMatrixParameters();
			if(matrixParams != null) {
				tenant = Optional.ofNullable(matrixParams.getFirst(Naming.Management.TENANT));
			}
		}
		return tenant;
	}
	
	/**
	 * @param uriInfo
	 * @return
	 */
	static MultivaluedMap<String, String> getParamters(final UriInfo uriInfo) {
		MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
		final List<PathSegment> segments = uriInfo.getPathSegments();
		if(segments != null && segments.size() > 0) {
			final PathSegment firstSegment = segments.get(0);
			final MultivaluedMap<String, String> matrixParams = firstSegment.getMatrixParameters();
			if(matrixParams != null) {
				params = firstSegment.getMatrixParameters();
			}
		}
		return params;
	}
    
    /**
     * @param webTarget
     * @param uriInfo
     * @return
     */
    static WebTarget buildTarget(
    		WebTarget webTarget,
    		final UriInfo uriInfo){
        if(uriInfo != null){
        	final List<PathSegment> segments = uriInfo.getPathSegments();
            if(segments != null){
            	final Iterator<PathSegment> segmentIt = segments.iterator();
            	final PathSegment firstSegemnt = segmentIt.next();
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
     * @param input
     * @param headers
     * @param baseUri
     * @return
     */
    static Builder buildHeaders(final Builder input, final HttpHeaders headers, final URI baseUri){
    	Builder builder = input;
        if(headers != null){
        	final List<MediaType> mediaTypes = headers.getAcceptableMediaTypes();
            if(mediaTypes != null){
                builder = builder.accept(mediaTypes.toArray(new MediaType[0]));
            }
            List<Locale> languages = headers.getAcceptableLanguages();
            if(languages == null || languages.isEmpty()) {
            	languages = Arrays.asList(Locale.getDefault());
            }
            builder = builder.acceptLanguage(languages.toArray(new Locale[0]));
            final Map<String, Cookie> cookies = headers.getCookies();
            if(cookies != null){
            	for(Entry<String, Cookie> entry : cookies.entrySet()) {
            		final Cookie value = entry.getValue();
            		builder = builder.cookie(value);
            	}
            }
            final MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
            if(requestHeaders.containsKey(HttpHeaders.AUTHORIZATION)){
            	builder = builder.header(HttpHeaders.AUTHORIZATION, headers.getHeaderString(HttpHeaders.AUTHORIZATION));
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
