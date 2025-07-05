package epf.gateway.util;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Optional;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.sse.SseEventSource;
import epf.naming.Naming;
import epf.util.StringUtil;

public interface RequestUtil {
	
	String LRA_HTTP_HEADER_PREFIX = "Long-Running-Action";
	
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
    
    static WebTarget buildTarget(
    		WebTarget webTarget,
    		final UriInfo uriInfo){
        if(uriInfo != null){
        	final List<PathSegment> segments = uriInfo.getPathSegments();
            if(segments != null){
            	webTarget = buildTarget(webTarget, segments);
            }
            webTarget = buildQueryParameters(webTarget, uriInfo);
        }
        return webTarget;
    }
    
    static WebTarget buildTarget(
    		WebTarget webTarget,
    		final List<PathSegment> segments) {
    	final Iterator<PathSegment> segmentIt = segments.iterator();
    	if(segmentIt.hasNext()) {
        	final PathSegment firstSegemnt = segmentIt.next();
        	webTarget = buildMatrixParameters(webTarget, firstSegemnt);
        	while(segmentIt.hasNext()) {
        		final PathSegment segment = segmentIt.next();
        		webTarget = webTarget.path(segment.getPath());
        		webTarget = buildMatrixParameters(webTarget, segment);
        	}
    	}
    	return webTarget;
    }
    
    static Builder buildHeaders(final Builder input, final HttpHeaders headers, final URI targetUrl, final boolean buildForwardedHeaders){
    	Builder builder = input;
    	final MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
        final MultivaluedHashMap<String, Object> forwardHeaders = new MultivaluedHashMap<String, Object>(requestHeaders);
        forwardHeaders.remove(HttpHeaders.CONTENT_LENGTH);
        forwardHeaders.remove(HttpHeaders.CONTENT_LENGTH.toLowerCase());
        Optional<Object> host = Optional.ofNullable(forwardHeaders.getFirst(HttpHeaders.HOST));
        if(!host.isPresent()) {
        	host = Optional.ofNullable(forwardHeaders.getFirst(HttpHeaders.HOST.toLowerCase()));
        }
    	forwardHeaders.remove(HttpHeaders.HOST);
    	forwardHeaders.remove(HttpHeaders.HOST.toLowerCase());
        builder = builder.headers(forwardHeaders);
        List<Locale> languages = headers.getAcceptableLanguages();
        if(languages == null || languages.isEmpty() || languages.get(0).getLanguage().isEmpty()) {
        	languages = Arrays.asList(Locale.getDefault());
            builder = builder.acceptLanguage(languages.toArray(new Locale[0]));
        }
        if(buildForwardedHeaders) {
            builder = buildForwardedHeaders(builder, host, headers, targetUrl);
        }
        return builder;
    }
    
    static Builder buildForwardedHeaders(Builder builder, final Optional<Object> host, final HttpHeaders headers, final URI targetUrl) {
    	builder = builder.header(HttpHeaders.HOST, targetUrl.getAuthority());
        final List<String> forwardedHost = headers.getRequestHeader(Naming.Gateway.Headers.X_FORWARDED_HOST);
        final List<String> newForwardedHost = new ArrayList<>(forwardedHost);
        if(host.isPresent()) {
            newForwardedHost.add(host.get().toString());
        }
        builder = builder.header(Naming.Gateway.Headers.X_FORWARDED_HOST, StringUtil.valueOf(newForwardedHost, ","));
        return builder;
    }
    
    static Builder buildLRAHeaders(Builder builder, final Response response) {
    	for(final Entry<String, List<Object>> entry : response.getHeaders().entrySet()) {
			if(entry.getKey().startsWith(LRA_HTTP_HEADER_PREFIX)) {
				builder = builder.header(entry.getKey(), entry.getValue().get(0));
			}
		}
    	return builder;
    }
    
    static Response buildInvoke(
    		final Builder invoker,
    		final String method, 
    		final MediaType type, 
    		final InputStream body) {
    	if(body == null || type == null) {
    		return invoker.method(method);
    	}
    	return invoker.method(method, Entity.entity(body, type));
    }
    
    static SseEventSource.Builder buildSource(final SseEventSource.Builder builder){
    	return builder;
    }
    
    static Optional<String> getTenant(final UriInfo uriInfo){
    	return Optional.ofNullable(uriInfo.getPathSegments().get(0).getMatrixParameters().getFirst(Naming.Management.TENANT));
    }
}
