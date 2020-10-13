/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import openup.error.ExceptionHandler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.jwt.config.Names;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import openup.config.Config;
import openup.persistence.Application;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("security")
@RolesAllowed("ANY_ROLE")
public class Security implements Serializable {
    
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    @Inject
    private Application persistence;
    
    @Inject
    private TokenGenerator generator;
    
    @Inject
    @ConfigProperty(name = Names.ISSUER)
    private String issuer;
    
    @Inject
    @ConfigProperty(name = Config.JWT_EXPIRE_DURATION)
    private Long jwtExpDuration;
    
    @Inject
    @ConfigProperty(name = Config.JWT_EXPIRE_TIMEUNIT)
    private String jwtExpTimeUnit;
    
    @Inject
    private Event<Token> event;
    
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
            summary = "login", 
            description = "login",
            operationId = "login"
    )
    @RequestBody(
            required = true,
            content = {
                @Content(
                        mediaType = MediaType.APPLICATION_FORM_URLENCODED,
                        example = "{\"username\":\"\",\"password\":\"\"}"
                )
            }
    )
    @APIResponse(
            name = "token", 
            description = "Token",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.TEXT_PLAIN
            )
    )
    @Bulkhead(value = 10, waitingTaskQueue = 16)
    @Timeout(8000)
    @Fallback(value = ExceptionHandler.class, applyOn = {Exception.class})
    @CircuitBreaker(requestVolumeThreshold = 40, failureRatio = 0.618, successThreshold = 15)
    public Response login(
            @FormParam("username")
            String username,
            @FormParam("password")
            String password, 
            @QueryParam("url")
            URL url,
            @Context
            HttpServletRequest request ) throws Exception{
        ResponseBuilder response = Response.ok();
        
        persistence.createFactory(username, password);
        
        Token jwt = buildToken(username);
        
        buildTokenID(jwt, request);
        
        Set<String> aud = new HashSet<>();
        aud.add(url.toString());
        jwt.setAudience(aud);
        
        Set<String> roles = getUserRoles(username, persistence.getDefaultManager());
        jwt.setGroups(roles);
        
        String token = generator.generate(jwt);
        
        jwt.setRawToken(token);
        event.fire(jwt);
        
        return response.entity(token).build();
    }
    
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(
            summary = "runAs", 
            description = "runAs",
            operationId = "runAs"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_FORM_URLENCODED,
                    example = "{\"runAs\":\"ADMIN\"}"
            )
    )
    @APIResponse(
            name = "token", 
            description = "Token",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.TEXT_PLAIN
            )
    )
    public Response runAs(
            @FormParam("runAs") 
            String role,
            @Context
            SecurityContext context, 
            @Context 
            UriInfo uriInfo,
            @Context
            HttpServletRequest request
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Principal principal = context.getUserPrincipal();
        Token jwt = buildToken(principal.getName());
        buildTokenID(jwt, request);
        buildAudience(jwt, uriInfo.getBaseUri());
        if(buildRoles(jwt, principal, role, response)){
            String token = generator.generate(jwt);
            response.entity(token);
            jwt.setRawToken(token);
            event.fire(jwt);
        }
        return response.build();
    }
    
    void buildAudience(Token token, URI uri){
        Set<String> aud = new HashSet<>();
        aud.add(String.format("%s://%s:%s/", uri.getScheme(), uri.getHost(), uri.getPort()));
        token.setAudience(aud);
    }
    
    void buildTokenID(Token token, HttpServletRequest request){
        if(request.isRequestedSessionIdValid()){
            token.setTokenID(request.getRequestedSessionId());
        }
        else{
            token.setTokenID(token.getName() + UUID.randomUUID());
        }
    }
    
    boolean buildRoles(Token token, Principal principal, String role, ResponseBuilder response){
        boolean ok = true;
        if("ADMIN".equalsIgnoreCase(role)){
            if(isAdmin(principal.getName(), persistence.getDefaultManager())){
                Set<String> groups = new HashSet<>();
                groups.add("ADMIN");
                token.setGroups(groups);
            }
            else{
                response.status(Response.Status.FORBIDDEN);
                ok = false;
            }
        }
        else{
            Set<String> roles = getUserRoles(principal.getName(), persistence.getDefaultManager());
            token.setGroups(roles);
        }
        return ok;
    }
    
    Token buildToken(String username) throws Exception{
        Token jwt = new Token();
        long time = new Date().getTime() / 1000;
        jwt.setIssuedAtTime(time);
        jwt.setExpirationTime(time + jwtExpDuration * ChronoUnit.valueOf(jwtExpTimeUnit).getDuration().toSeconds());
        jwt.setIssuer(issuer);
        jwt.setSubject(username);
        jwt.setAudience(new HashSet<>());
        return jwt;
    }
    
    Set<String> getUserRoles(String userName, EntityManager manager){
        Query query = manager.createNamedQuery("Role.GetUserRoles");
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, userName.toUpperCase());
        query.setParameter(3, userName.toUpperCase());
        query.setParameter(4, userName.toUpperCase());
        Stream<?> result = query.getResultStream();
        return result.map(Object::toString).collect(Collectors.toSet());
    }
    
    boolean isAdmin(String userName, EntityManager manager){
        Query query = manager.createNamedQuery("Role.IsAdmin");
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, "true");
        return query.getResultStream().count() > 0;
    }
}
