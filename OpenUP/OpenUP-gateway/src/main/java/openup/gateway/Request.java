/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.context.ManagedExecutor;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Request {
    
    private HttpHeaders headers;
    private UriInfo uriInfo;
    private Client client;
    private URI uri;
    private static final String OPENUP_URL = "openup.url";
    
    @Inject 
    private ManagedExecutor executor;
    
    @Inject
    private ClientQueue clients;
    
    public CompletionStage<Response> request(
            String method, 
            InputStream in) throws Exception{
        return executor.supplyAsync(() -> uri = buildUri(uriInfo))
                .thenApply(newUri -> client = clients.poll(newUri))
                .thenApply(newClient -> buildTarget(newClient, uriInfo))
                .thenApply(target -> buildRequest(target, headers))
                .thenApply(request -> buildMethod(request, method, headers.getMediaType(), in))
                .thenApply(Request::buildResponse)
                .thenApply(ResponseBuilder::build)
                .whenComplete((res, ex) -> {
                    if(ex != null){
                        client.close();
                    }
                    else{
                        clients.add(uri, client);
                    }
                });
    }
    
    static URI buildUri(UriInfo uriInfo){
        UriBuilder builder = uriInfo.getBaseUriBuilder();
        List<PathSegment> segments = uriInfo.getPathSegments();
        if(segments != null && !segments.isEmpty()){
            builder = builder.path(segments.get(0).getPath());
        }
        return builder.build();
    }
    
    static WebTarget buildTarget(Client client, UriInfo uriInfo){
        String url = System.getenv(OPENUP_URL);
        WebTarget webTarget = client.target(url);
        if(uriInfo != null){
            List<PathSegment> segments = uriInfo.getPathSegments();
            if(segments != null){
                for(PathSegment segment : segments){
                    webTarget = webTarget.path(segment.getPath());
                    MultivaluedMap<String, String> matrixParams = segment.getMatrixParameters();
                    if(matrixParams != null){
                        for(Map.Entry<String, List<String>> matrixParam : matrixParams.entrySet()){
                        	Object[] values = new Object[matrixParam.getValue().size()];
                        	webTarget = webTarget.matrixParam(matrixParam.getKey(), (Object[])matrixParam.getValue().toArray(values));
                        }
                    }
                }
            }
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();        
            if(queryParams != null){
                for(Map.Entry<String, List<String>> queryParam : queryParams.entrySet()){
                	Object[] values = new Object[queryParam.getValue().size()];
                	webTarget = webTarget.queryParam(queryParam.getKey(), (Object[])queryParam.getValue().toArray(values));
                }
            }
        }
        return webTarget;
    }
    
    static Builder buildRequest(WebTarget target, HttpHeaders headers){
        Builder builder = target.request();
        if(headers != null){
            List<MediaType> mediaTypes = headers.getAcceptableMediaTypes();
            if(mediaTypes != null){
                builder = builder.accept(mediaTypes.toArray(new MediaType[mediaTypes.size()]));
            }
            List<Locale> languages = headers.getAcceptableLanguages();
            if(languages != null){
                builder = builder.acceptLanguage(languages.toArray(new Locale[languages.size()]));
            }
            Map<String, Cookie> cookies = headers.getCookies();
            if(cookies != null){
                for(Entry<String, Cookie> cookie : cookies.entrySet()){
                    builder = builder.cookie(cookie.getValue());
                }
            }
            MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
            if(requestHeaders.containsKey(HttpHeaders.AUTHORIZATION)){
                builder = builder.header(HttpHeaders.AUTHORIZATION, headers.getHeaderString(HttpHeaders.AUTHORIZATION));
            }
        }
        return builder;
    }
    
    static Response buildMethod(
            Builder builder, 
            String method, 
            MediaType type, 
            InputStream in){
        if(in != null && type != null){
            return builder.method(
                    method, 
                    Entity.entity(
                            in, 
                            type
                    )
            );
        }
        else{
            return builder.method(method);
        }
    }
    
    static ResponseBuilder buildResponse(Response res){
        ResponseBuilder builder = Response.fromResponse(res);
        Set<String> methods = res.getAllowedMethods();
        if(methods != null){
            builder = builder.allow(methods);
        }
        Map<String, NewCookie> cookies = res.getCookies();
        if(cookies != null){
            builder = builder.cookie(cookies.values().toArray(new NewCookie[cookies.values().size()]));
        }
        URI location = res.getLocation();
        if(location != null){
            builder = builder.contentLocation(location);
        }
        if(res.hasEntity()){
            Object entity = res.getEntity();
            if(entity != null){
                builder = builder.entity(entity);
            }
        }
        Locale lang = res.getLanguage();
        if(lang != null){
            builder = builder.language(lang);
        }
        Date modified = res.getLastModified();
        if(modified != null){
            builder = builder.lastModified(modified);
        }
        Set<Link> links = res.getLinks();
        if(links != null){
            builder = builder.links(links.toArray(new Link[links.size()]));
        }
        if(location != null){
            builder = builder.location(location);
        }
        MultivaluedMap<String, Object> resHeaders = res.getHeaders();
        if(resHeaders != null){
            builder = builder.replaceAll(resHeaders);
        }
        StatusType status = res.getStatusInfo();
        if(status != null){
            builder = builder.status(status);
        }
        EntityTag tag = res.getEntityTag();
        if(tag != null){
            builder = builder.tag(tag);
        }
        MediaType type = res.getMediaType();
        if(type != null){
            builder = builder.type(type);
        }
        return builder;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
