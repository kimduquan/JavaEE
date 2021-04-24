/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.security;

import java.io.Serializable;
import java.net.URL;
import java.security.Principal;
import java.time.temporal.ChronoUnit;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
import epf.client.security.Info;
import epf.client.security.Token;
import epf.schema.roles.Role;
import epf.service.persistence.Application;
import epf.service.persistence.Credential;
import epf.service.persistence.Session;
import epf.service.persistence.security.SecurityService;
import epf.util.logging.Log;

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
    
    /**
     * 
     */
    @Inject
    private transient Application persistence;
    
    /**
     * 
     */
    @Inject
    private transient TokenGenerator generator;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = Names.ISSUER)
    private transient String issuer;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXPIRE_DURATION)
    private transient Long jwtExpDuration;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXPIRE_TIMEUNIT)
    private transient ChronoUnit jwtExpTimeUnit;
    
    /**
     * 
     */
    @Inject
    private transient SecurityService service;
    
    /**
     * 
     */
    @Context 
    private transient SecurityContext context;
    
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
            final String unit,
            final String username,
            final String passwordHash,
            final URL url) {
    	final long time = System.currentTimeMillis() / 1000;
    	final Credential credential = persistence.putContext(unit)
                .putCredential(
                        username, 
                        unit, 
                        passwordHash
                );
    	final TokenBuilder builder = new TokenBuilder(issuer, service);
        final Token jwt = builder
        		.expire(jwtExpTimeUnit, jwtExpDuration)
        		.fromCredential(credential)
        		.generator(generator)
        		.time(time)
        		.url(url)
        		.userName(username)
        		.build();
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
    @Log
    public String logOut(final String unit) {
    	final Session session = removeSession(unit);
        if(session != null){
            session.close();
            return context.getUserPrincipal().getName();
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
    public Token authenticate(final String unit) {
    	if(getSession(unit) != null){
    		final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    		return new TokenBuilder(issuer, service).build(jwt);
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    }
    
    /**
     * @param unit
     * @return
     */
    protected Session removeSession(final String unit) {
    	Session session = null;
    	final Principal principal = context.getUserPrincipal();
    	final epf.service.persistence.Context ctx = persistence.getContext(unit);
        if(principal != null && ctx != null){
        	final Credential credential = ctx.getCredential(principal.getName());
            if(credential != null && principal instanceof JsonWebToken){
            	final JsonWebToken jwt = (JsonWebToken)principal;
                session = credential.removeSession(jwt.getIssuedAtTime());
            }
        }
        return session;
    }
    
    /**
     * @param unit
     * @return
     */
    protected Session getSession(final String unit) {
    	Session session = null;
    	final Principal principal = context.getUserPrincipal();
    	final epf.service.persistence.Context ctx = persistence.getContext(unit);
    	Credential credential = null;
    	JsonWebToken jwt = null;
        if(principal != null && ctx != null){
        	credential = ctx.getCredential(principal.getName());
        }
        if(credential != null && principal instanceof JsonWebToken){
        	jwt = (JsonWebToken)principal;
        	session = credential.getSession(jwt.getIssuedAtTime());
        }
        if(session != null && !session.checkExpirationTime(jwt.getExpirationTime())) {
        	throw new ForbiddenException();
        }
        return session;
    }
    
    /**
     * @param unit
     * @return
     */
    protected Credential getCredential(final String unit) {
    	final Principal principal = context.getUserPrincipal();
    	final epf.service.persistence.Context ctx = persistence.getContext(unit);
    	if(principal != null && ctx != null) {
    		final Credential credential = ctx.getCredential(principal.getName());
    		if(credential != null) {
    			return credential;
    		}
    	}
    	throw new ForbiddenException();
    }

	@Override
	public void update(final String unit, final Info info) {
		final Credential credential = getCredential(unit);
		service.setUserPassword(credential.getDefaultManager(), context.getUserPrincipal().getName(), info.getPassword().toCharArray());
	}
}
