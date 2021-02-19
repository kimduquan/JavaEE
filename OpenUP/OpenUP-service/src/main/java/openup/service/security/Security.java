/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.security;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.jwt.config.Names;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import epf.client.config.ConfigNames;
import epf.client.security.Token;
import epf.schema.roles.Role;
import openup.service.persistence.Application;
import openup.service.persistence.Credential;
import openup.service.persistence.Session;

/**
 *
 * @author FOXCONN
 */
@Path("security")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class Security implements epf.client.security.Security, Serializable {
    
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
    
    @Context 
    private SecurityContext context;
    
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
                        example = "{\"username\":\"\",\"password_hash\":\"\"}"
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
    public String login(
            String unit,
            String username,
            String password_hash,
            URL url) throws Exception{
    	long time = System.currentTimeMillis() / 1000;
    	Credential credential = persistence.putContext(unit)
                .putCredential(
                        username, 
                        unit, 
                        password_hash
                );
        Token jwt = buildToken(username, time);
        buildAudience(jwt, url.toURI());
        buildGroups(jwt, username, credential.getDefaultManager());
        jwt = generator.generate(jwt);
        credential.putSession(jwt.getIssuedAtTime());
        return jwt.getRawToken();
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
    public String logOut(
            String unit
    ) throws Exception{
    	Session session = removeSession(unit);
        if(session != null){
            session.close();
            String name = context.getUserPrincipal().getName();
            return name;
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
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
    public Token authenticate(String unit) throws Exception{
    	if(getSession(unit) != null){
            JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
            Token token = buildToken(jwt);
            return token;
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
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
    
    Token buildToken(String username, long time) throws Exception{
        Token jwt = new Token();
        jwt.setIssuedAtTime(time);
        jwt.setExpirationTime(time + Duration.of(jwtExpDuration, jwtExpTimeUnit).getSeconds());
        jwt.setIssuer(issuer);
        jwt.setName(username);
        jwt.setSubject(username);
        return jwt;
    }
    
    void buildGroups(Token token, String userName, EntityManager manager) {
    	Set<String> groups = new HashSet<>();
    	groups.add(Role.DEFAULT_ROLE);
    	token.setGroups(groups);
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
    
    Session removeSession(String unit) throws Exception{
        Principal principal = context.getUserPrincipal();
        if(principal != null){
            openup.service.persistence.Context ctx = persistence.getContext(unit);
            if(ctx != null){
                Credential credential = ctx.getCredential(principal.getName());
                if(credential != null){
                    if(principal instanceof JsonWebToken){
                        JsonWebToken jwt = (JsonWebToken)principal;
                        return credential.removeSession(jwt.getIssuedAtTime());
                    }
                }
            }
        }
        return null;
    }
    
    Session getSession(String unit) throws Exception{
        Principal principal = context.getUserPrincipal();
        if(principal != null){
            openup.service.persistence.Context ctx = persistence.getContext(unit);
            if(ctx != null){
                Credential credential = ctx.getCredential(principal.getName());
                if(credential != null){
                    if(principal instanceof JsonWebToken){
                        JsonWebToken jwt = (JsonWebToken)principal;
                        Session session = credential.getSession(jwt.getIssuedAtTime());
                        if(session != null) {
                            if(session.checkExpirationTime(jwt.getExpirationTime())){
                                return session;
                            }
                            else {
                            	throw new ForbiddenException();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
