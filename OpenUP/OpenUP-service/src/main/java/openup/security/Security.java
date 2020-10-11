/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import java.io.Serializable;
import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
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
import org.eclipse.microprofile.openapi.annotations.enums.ParameterStyle;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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
                        schema = @Schema(
                                type = SchemaType.OBJECT
                        )
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
    @Timeout(40000)
    @Fallback(value = ExceptionHandler.class, applyOn = {Exception.class})
    @CircuitBreaker(requestVolumeThreshold = 40, failureRatio = 0.618, successThreshold = 15)
    public Response login(
            @FormParam("username")
            String username,
            @FormParam("password")
            String password ) throws Exception{
        ResponseBuilder response = Response.ok();
        persistence.createFactory(username, password);
        Set<String> roles = getUserRoles(username, persistence.getDefaultManager());
        Token jwt = buildToken(username);
        jwt.setGroups(roles);
        String token = generator.generate(jwt);
        return response.entity(token).build();
    }
    
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
            summary = "runAs", 
            description = "runAs",
            operationId = "runAs"
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
            SecurityContext context
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Principal principal = context.getUserPrincipal();
        Token jwt = buildToken(principal.getName());
        if(buildRoles(jwt, principal, role, response)){
            String token = generator.generate(jwt);
            response.entity(token);
        }
        return response.build();
    }
    
    boolean buildRoles(Token token, Principal principal, String role, ResponseBuilder response){
        boolean ok = true;
        if("ADMIN".equalsIgnoreCase(role)){
            if(isAdmin(principal.getName(), persistence.getDefaultManager())){
                token.setGroups(Set.of("OpenUP"));
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
        jwt.setExp(
                new Date().getTime() 
                        + jwtExpDuration 
                                * ChronoUnit.valueOf(jwtExpTimeUnit)
                                        .getDuration()
                                        .toMillis()
        );
        jwt.setIss(issuer);
        jwt.setJti(username + UUID.randomUUID());
        jwt.setKid("");
        jwt.setSub(username);
        jwt.setUpn(username);
        return jwt;
    }
    
    Set<String> getUserRoles(String userName, EntityManager manager){
        Set<String> roles = new HashSet<>();
        Query query = manager.createNamedQuery("Role.GetUserRoles");
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, userName.toUpperCase());
        query.setParameter(3, userName.toUpperCase());
        query.setParameter(4, userName.toUpperCase());
        Stream<?> result = query.getResultStream();
        result.forEach(value -> {
            roles.add(value.toString());
        });
        return roles;
    }
    
    boolean isAdmin(String userName, EntityManager manager){
        Query query = manager.createNamedQuery("Role.IsAdmin");
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, "true");
        return query.getResultStream().count() > 0;
    }
}
