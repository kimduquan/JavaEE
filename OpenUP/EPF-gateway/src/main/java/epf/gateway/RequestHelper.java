/**
 * 
 */
package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
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
import epf.util.Var;

/**
 * @author PC
 *
 */
public final class RequestHelper {
	
	/**
	 * 
	 */
	private static final int MIN_PATHS_COUNT = 1;
	
	/**
	 * 
	 */
	private RequestHelper(){
		
	}

	/**
     * @param uriInfo
     * @param registry
     * @return
     */
    public static URI buildUri(final UriInfo uriInfo, final Registry registry) {
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
    public static WebTarget buildTarget(final Client client,final  UriInfo uriInfo, final URI serviceUri){
    	final Var<WebTarget> webTarget = new Var<>(client.target(serviceUri));
        if(uriInfo != null){
        	final List<PathSegment> segments = uriInfo.getPathSegments();
            if(segments != null){
            	segments.forEach(segment -> {
            		webTarget.set(target -> target.path(segment.getPath()));
            		final MultivaluedMap<String, String> matrixParams = segment.getMatrixParameters();
                    if(matrixParams != null){
                    	matrixParams.forEach((key, value) -> {
                    		if(value == null) {
                        		webTarget.set(target -> target.matrixParam(key));
                        	}
                        	else {
                            	webTarget.set(target -> target.matrixParam(key, value.toArray(new Object[0])));
                        	}
                        });
                    }
                });
            }
            final MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();        
            if(queryParams != null){
            	queryParams.forEach((key, value) -> {
            		if(value == null) {
                		webTarget.set(target -> target.queryParam(key));
                	}
                	else {
                		webTarget.set(target -> target.queryParam(key, value.toArray(new Object[0])));
                	}
            	});
            }
        }
        return webTarget.get();
    }
    
    /**
     * @param target
     * @param headers
     * @return
     */
    public static Builder buildRequest(final WebTarget target, final HttpHeaders headers){
    	final Var<Builder> builder = new Var<>(target.request());
        if(headers != null){
        	final List<MediaType> mediaTypes = headers.getAcceptableMediaTypes();
            if(mediaTypes != null){
                builder.set(b -> b.accept(mediaTypes.toArray(new MediaType[0])));
            }
            final List<Locale> languages = headers.getAcceptableLanguages();
            if(languages != null){
                builder.set(b -> b.acceptLanguage(languages.toArray(new Locale[0])));
            }
            final Map<String, Cookie> cookies = headers.getCookies();
            if(cookies != null){
            	cookies.forEach((key, value) -> {
            		builder.set(b -> b.cookie(value));
            	});
            }
            final MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
            if(requestHeaders.containsKey(HttpHeaders.AUTHORIZATION)){
            	builder.set(b -> b.header(HttpHeaders.AUTHORIZATION, headers.getHeaderString(HttpHeaders.AUTHORIZATION)));
            }
        }
        return builder.get();
    }
    
    /**
     * @param builder
     * @param method
     * @param type
     * @param in
     * @return
     */
    public static Response buildMethod(
    		final Builder builder, 
    		final String method, 
    		final MediaType type, 
    		final InputStream body){
    	Response response;
    	if(body == null || type == null) {
    		response = builder.method(method);
    	}
    	else {
    		response = builder.method(
                    method, 
                    Entity.entity(
                            body, 
                            type
                    )
            );
        }
    	return response;
    }
    
    /**
     * @param link
     * @param uriInfo
     * @return
     */
    public static Link buildLink(final Link link, final UriInfo uriInfo) {
    	final URI linkUri = link.getUri();
    	URI uri = linkUri;
    	if(!linkUri.getScheme().startsWith("ws")) {
    		uri = buildUri(link, uriInfo);
    	}
    	return Link.fromLink(link).uri(uri).build();
    }
    
    /**
     * @param link
     * @param uriInfo
     * @return
     */
    private static URI buildUri(final Link link, final UriInfo uriInfo) {
    	final URI linkUri = link.getUri();
    	final String[] linkPaths = linkUri.getPath().split("/");
    	String path = "";
    	if(linkPaths.length > MIN_PATHS_COUNT) {
    		path = String.join("/", Arrays.asList(linkPaths).subList(2, linkPaths.length));
    	}
    	final String linkScheme = linkUri.getScheme();
    	final int linkPort = linkUri.getPort();
    	return uriInfo.getBaseUriBuilder().path(path).scheme(linkScheme).port(linkPort).build();
    }
    
    /**
     * @param res
     * @param uriInfo
     * @return
     */
    public static ResponseBuilder buildResponse(final Response res, final UriInfo uriInfo){
        ResponseBuilder builder = Response.fromResponse(res);
        final Set<String> methods = res.getAllowedMethods();
        if(methods != null){
            builder = builder.allow(methods);
        }
        final Map<String, NewCookie> cookies = res.getCookies();
        if(cookies != null){
            builder = builder.cookie(cookies.values().toArray(new NewCookie[0]));
        }
        final URI location = res.getLocation();
        if(location != null){
            builder = builder.contentLocation(location);
        }
        if(res.hasEntity()){
        	final Object entity = res.getEntity();
            if(entity != null){
                builder = builder.entity(entity);
            }
        }
        final Locale lang = res.getLanguage();
        if(lang != null){
            builder = builder.language(lang);
        }
        final Date modified = res.getLastModified();
        if(modified != null){
            builder = builder.lastModified(modified);
        }
        Set<Link> links = res.getLinks();
        if(links != null){
        	links = links
        			.stream()
        			.map(link -> buildLink(link, uriInfo))
        			.collect(Collectors.toSet());
        	builder = builder.links().links(links.toArray(new Link[0]));
        }
        if(location != null){
            builder = builder.location(location);
        }
        final StatusType status = res.getStatusInfo();
        if(status != null){
            builder = builder.status(status);
        }
        final EntityTag tag = res.getEntityTag();
        if(tag != null){
            builder = builder.tag(tag);
        }
        final MediaType type = res.getMediaType();
        if(type != null){
            builder = builder.type(type);
        }
        return builder;
    }
}
