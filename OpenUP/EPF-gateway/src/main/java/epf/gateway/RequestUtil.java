/**
 * 
 */
package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletionStage;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.sse.SseEventSource;
import epf.naming.Naming;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public interface RequestUtil {
	
	/**
	 * 
	 */
	int MIN_PATHS_COUNT = 1;

	/**
     * @param uriInfo
     * @param registry
     * @return
     */
    static URI buildUri(final UriInfo uriInfo, final Registry registry) {
    	URI uri = null;
    	if(uriInfo.getPathSegments() != null && !uriInfo.getPathSegments().isEmpty()) {
    		final String service = uriInfo.getPathSegments().get(0).getPath();
    		uri = registry.lookup(service);
    	}
    	if(uri != null) {
    		final String[] paths = uri.getPath().split("/");
    		if(paths.length > MIN_PATHS_COUNT) {
        		uri = UriBuilder.fromUri(uri).replacePath(paths[1]).build();
        	}
    	}
    	if(uri == null) {
    		uri = uriInfo.getBaseUri();
    	}
    	return uri;
    }
    
    /**
     * @param client
     * @param uriInfo
     * @param serviceUri
     * @return
     */
    static WebTarget buildTarget(final WebTarget input,final  UriInfo uriInfo, final URI serviceUri){
    	WebTarget webTarget = input;
        if(uriInfo != null){
        	final List<PathSegment> segments = uriInfo.getPathSegments();
            if(segments != null){
            	final Iterator<PathSegment> segmentIt = segments.iterator();
            	while(segmentIt.hasNext()) {
            		final PathSegment segment = segmentIt.next();
            		webTarget = webTarget.path(segment.getPath());
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
            	}
            }
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
        }
        return webTarget;
    }
    
    /**
     * @param target
     * @param headers
     * @return
     */
    static Builder buildRequest(final Builder input, final HttpHeaders headers, final URI baseUri){
    	Builder builder = input;
        if(headers != null){
        	final List<MediaType> mediaTypes = headers.getAcceptableMediaTypes();
            if(mediaTypes != null){
                builder = builder.accept(mediaTypes.toArray(new MediaType[0]));
            }
            final List<Locale> languages = headers.getAcceptableLanguages();
            if(languages != null){
                builder = builder.acceptLanguage(languages.toArray(new Locale[0]));
            }
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
     * @param link
     * @param baseUri
     * @return
     */
    static Link buildLink(final Link link, final URI baseUri) {
    	final URI uri = buildUri(link, baseUri);
    	return Link.fromLink(link).uri(uri).build();
    }
    
    /**
     * @param link
     * @param baseUri
     * @return
     */
    static URI buildUri(final Link link, final URI baseUri) {
    	final URI linkUri = link.getUri();
    	final String[] linkPaths = linkUri.getPath().split("/");
    	String path = "";
    	if(linkPaths.length > MIN_PATHS_COUNT) {
    		path = String.join("/", Arrays.asList(linkPaths).subList(2, linkPaths.length));
    	}
    	final String linkScheme = linkUri.getScheme();
    	final int linkPort = linkUri.getPort();
    	return UriBuilder.fromUri(baseUri).path(path).scheme(linkScheme).port(linkPort).build();
    }
    
    /**
     * @param builder
     * @param res
     * @param uriInfo
     * @return
     */
    static Response buildResponse(final Response response, final URI baseUri){
		ResponseBuilder builder = Response.fromResponse(response);
		final Set<String> methods = response.getAllowedMethods();
		if(methods != null){
			builder = builder.allow(methods);
		}
		final Map<String, NewCookie> cookies = response.getCookies();
		if(cookies != null){
			builder = builder.cookie(cookies.values().toArray(new NewCookie[0]));
		}
		final URI location = response.getLocation();
		if(location != null){
			builder = builder.contentLocation(location);
		}
		final Locale lang = response.getLanguage();
		if(lang != null){
			builder = builder.language(lang);
		}
		final Date modified = response.getLastModified();
		if(modified != null){
			builder = builder.lastModified(modified);
		}
		Set<Link> links = response.getLinks();
		if(links != null){
			links = links
					.stream()
					.map(link -> buildLink(link, baseUri))
					.collect(Collectors.toSet());
			builder = builder.links().links(links.toArray(new Link[0]));
		}
		if(location != null){
			builder = builder.location(location);
		}
		final StatusType status = response.getStatusInfo();
		if(status != null){
			builder = builder.status(status);
		}
		final EntityTag tag = response.getEntityTag();
		if(tag != null){
			builder = builder.tag(tag);
		}
		final MediaType type = response.getMediaType();
		if(type != null){
			builder = builder.type(type);
		}
		return builder.build();
    }
    
    /**
     * @param builder
     * @return
     */
    static SseEventSource.Builder buildSource(final SseEventSource.Builder builder){
    	return builder;
    }
}
