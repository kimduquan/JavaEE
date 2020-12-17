/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import openup.client.security.Token;
import epf.schema.roles.Role;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import openup.epf.schema.Roles;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.config.Names;
import openup.persistence.Application;
import openup.persistence.Credential;
import openup.persistence.Session;
import org.eclipse.microprofile.jwt.JsonWebToken;
import openup.client.config.ConfigNames;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@Path("security")
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class Security implements openup.client.security.Security, Serializable {
    
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
    @ConfigProperty(name = ConfigNames.JWT_EXPIRE_DURATION)
    private Long jwtExpDuration;
    
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXPIRE_TIMEUNIT)
    private ChronoUnit jwtExpTimeUnit;
    
    @PermitAll
    @Override
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
    @APIResponse(
            name = "Unauthorized", 
            description = "Unauthorized",
            responseCode = "401"
    )
    public Response login(
            String username,
            String password,
            URL url) throws Exception{
        ResponseBuilder response = Response.ok();
        
        long time = System.currentTimeMillis() / 1000;
        persistence.putCredential(username, password, time);
        
        Token jwt = buildToken(username, time);
        
        buildTokenID(jwt);
        
        buildAudience(jwt, url.toURI());
        
        Set<String> roles = getUserRoles(username, persistence.getDefaultManager());
        jwt.setGroups(roles);
        
        String token = generator.generate(jwt);
        jwt.setRawToken(token);
        
        return response.entity(token).build();
    }
    
    @Override
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
            String role,
            SecurityContext context,
            UriInfo uriInfo
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Principal principal = context.getUserPrincipal();
        long time = System.currentTimeMillis() / 1000;
        Token jwt = buildToken(principal.getName(), time);
        buildTokenID(jwt);
        buildAudience(jwt, uriInfo.getBaseUri());
        if(buildRoles(jwt, principal, role, response)){
            String token = generator.generate(jwt);
            response.entity(token);
            jwt.setRawToken(token);
        }
        return response.build();
    }
    
    @Override
    @Operation(
            summary = "logOut", 
            description = "logOut",
            operationId = "logOut"
    )
    @APIResponse(
            name = "OK", 
            description = "OK",
            responseCode = "200"
    )
    public Response logOut(
            SecurityContext context
            ) throws Exception{
        ResponseBuilder response = Response.status(Response.Status.UNAUTHORIZED);
        Principal principal = context.getUserPrincipal();
        if(principal != null){
            Credential credential = persistence.getCredential(principal.getName());
            if(credential != null){
                Session session = credential.getSession(principal);
                if(session != null){
                    credential.removeSession(principal);
                    response = Response.ok(principal.getName());
                }
            }
        }
        return response.build();
    }
    
    @Override
    @Operation(
            summary = "authenticate", 
            description = "authenticate",
            operationId = "authenticate"
    )
    @APIResponse(
            name = "OK", 
            description = "OK",
            responseCode = "200"
    )
    @APIResponse(
            name = "Unauthorized", 
            description = "Unauthorized",
            responseCode = "401"
    )
    public Response authenticate(SecurityContext context){
        ResponseBuilder response = Response.status(Response.Status.UNAUTHORIZED);
        Principal principal = context.getUserPrincipal();
        if(principal instanceof JsonWebToken){
            JsonWebToken jwt = (JsonWebToken) principal;
            Token token = buildToken(jwt);
            response.entity(token).status(Response.Status.OK);
        }
        return response.build();
    }
    
    void buildAudience(Token token, URI uri){
        Set<String> aud = new HashSet<>();
        aud.add(String.format(
                AUDIENCE_URL_FORMAT, 
                uri.getScheme(), 
                uri.getHost(), 
                uri.getPort()));
        token.setAudience(aud);
    }
    
    void buildTokenID(Token token){
        token.setTokenID(
                String.format(
                        TOKEN_ID_FORMAT,
                        token.getName(), 
                        UUID.randomUUID(),
                        System.currentTimeMillis()));
    }
    
    boolean buildRoles(Token token, Principal principal, String role, ResponseBuilder response){
        boolean ok = true;
        if(Roles.ADMIN.equalsIgnoreCase(role)){
            if(isAdmin(principal.getName(), persistence.getDefaultManager())){
                Set<String> groups = new HashSet<>();
                groups.add(Roles.ADMIN);
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
    
    Token buildToken(String username, long time) throws Exception{
        Token jwt = new Token();
        jwt.setIssuedAtTime(time);
        jwt.setExpirationTime(time + Duration.of(jwtExpDuration, jwtExpTimeUnit).getSeconds());
        jwt.setIssuer(issuer);
        jwt.setSubject(username);
        return jwt;
    }
    
    Set<String> getUserRoles(String userName, EntityManager manager){
        Query query = manager.createNamedQuery(Role.GET_USER_ROLES);
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, userName.toUpperCase());
        Stream<?> result = query.getResultStream();
        return result.map(Object::toString).collect(Collectors.toSet());
    }
    
    boolean isAdmin(String userName, EntityManager manager){
        Query query = manager.createNamedQuery(Role.IS_ADMIN);
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, String.valueOf(true));
        return query.getResultStream().count() > 0;
    }
    
    Token buildToken(JsonWebToken jwt){
        Token token = new Token();
        token.setAudience(jwt.getAudience());
        token.setExpirationTime(jwt.getExpirationTime());
        token.setGroups(jwt.getGroups());
        token.setIssuedAtTime(jwt.getIssuedAtTime());
        token.setIssuer(jwt.getIssuer());
        token.setName(jwt.getName());
        token.setRawToken(jwt.getRawToken());
        token.setSubject(jwt.getSubject());
        token.setTokenID(jwt.getTokenID());
        return token;
    }
}
