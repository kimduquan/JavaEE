/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.security;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.jwt.config.Names;
import epf.client.EPFException;
import epf.client.security.CredentialInfo;
import epf.client.security.Token;
import epf.client.security.jwt.JWTConfig;
import epf.persistence.context.Application;
import epf.persistence.context.Credential;
import epf.persistence.context.Session;
import epf.schema.roles.Role;
import openup.schema.Naming;

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
    private transient IdentityStore identityStore;
    
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
    @ConfigProperty(name = JWTConfig.EXPIRE_DURATION)
    private transient Long jwtExpDuration;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = JWTConfig.EXPIRE_TIMEUNIT)
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
    public String login(
            final String username,
            final String passwordHash,
            final URL url) {
    	final Credential credential = persistence.putContext()
                .putCredential(
                        username,
                        passwordHash
                );
    	final Set<String> roles = getRoles(credential.getDefaultManager(), username);
    	final TokenBuilder builder = new TokenBuilder(issuer, roles);
    	final long time = Instant.now().getEpochSecond();
        final Token jwt = builder
        		.expire(jwtExpTimeUnit, jwtExpDuration)
        		.generator(generator)
        		.time(time)
        		.url(url)
        		.userName(username)
        		.build();
        credential.putSession(jwt.getTokenID());
        return jwt.getRawToken();
    }
    
    @Override
    public String logOut() {
    	final Session session = removeSession(context, persistence);
        if(session != null){
            session.close();
            return context.getUserPrincipal().getName();
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    }
    
    @Override
    public Token authenticate() {
    	if(getSession(context, persistence) != null){
    		final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    		final Token token = new TokenBuilder(issuer, jwt.getGroups()).build(jwt);
    		token.setRawToken(null);
    		return token;
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    }
    
    /**
     * @param context
     * @param persistence
     * @return
     */
    protected Session removeSession(final SecurityContext context, final Application persistence) {
    	final Principal principal = context.getUserPrincipal();
    	final epf.persistence.context.Context ctx = persistence.getContext();
        if(principal != null && ctx != null){
        	final Credential credential = ctx.getCredential(principal.getName());
            if(credential != null && principal instanceof JsonWebToken){
            	final JsonWebToken jwt = (JsonWebToken)principal;
                return credential.removeSession(jwt.getTokenID());
            }
        }
        throw new ForbiddenException();
    }
    
    /**
     * @param context
     * @param persistence
     * @return
     */
    protected static Session getSession(final SecurityContext context, final Application persistence) {
    	Session session = null;
    	final Principal principal = context.getUserPrincipal();
    	final epf.persistence.context.Context ctx = persistence.getContext();
    	Credential credential = null;
    	JsonWebToken jwt = null;
        if(principal != null && ctx != null){
        	credential = ctx.getCredential(principal.getName());
        }
        if(credential != null && principal instanceof JsonWebToken){
        	jwt = (JsonWebToken)principal;
        	session = credential.getSession(jwt.getTokenID());
        }
        return session;
    }
    
    /**
     * @param context
     * @param persistence
     * @return
     */
    protected static Credential getCredential(final SecurityContext context, final Application persistence) {
    	final Principal principal = context.getUserPrincipal();
    	final epf.persistence.context.Context ctx = persistence.getContext();
    	if(principal != null && ctx != null) {
    		final Credential credential = ctx.getCredential(principal.getName());
    		if(credential != null) {
    			return credential;
    		}
    	}
    	throw new ForbiddenException();
    }
    
    /**
     * @param manager
     * @return
     */
    protected static Set<String> getRoles(final EntityManager manager, final String name){
    	return manager.createNamedQuery(Naming.FIND_ROLES_BY_NAME, openup.schema.roles.Role.class)
    			.setParameter("name", name)
    			.getResultStream()
    			.flatMap(role -> role.getRoles().stream())
    			.map(role -> role.getName())
    			.collect(Collectors.toSet());
    }

	@Override
	public void update(final CredentialInfo info) {
		final Credential credential = getCredential(context, persistence);
		service.setUserPassword(credential.getDefaultManager(), context.getUserPrincipal().getName(), info.getPassword().toCharArray());
	}

	@Override
	public String revoke() {
		final Session session = removeSession(context, persistence);
		if(session != null) {
			session.close();
			if(context.getUserPrincipal() instanceof JsonWebToken) {
				final JsonWebToken jsonWebToken = (JsonWebToken) context.getUserPrincipal();
				try {
					final URL url = new URL(jsonWebToken.getAudience().iterator().next());
					final TokenBuilder builder = new TokenBuilder(issuer, jsonWebToken.getGroups());
					final Credential credential = getCredential(context, persistence);
					final long time = Instant.now().getEpochSecond();
			        final Token jwt = builder
			        		.expire(jwtExpTimeUnit, jwtExpDuration)
			        		.generator(generator)
			        		.time(time)
			        		.url(url)
			        		.userName(jsonWebToken.getName())
			        		.build();
			        credential.putSession(jwt.getTokenID());
			        return jwt.getRawToken();
				} 
				catch (MalformedURLException e) {
					throw new EPFException(e);
				}
			}
		}
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
	}

	@PermitAll
	@Override
	public String newOneTimePassword(final String username, final String passwordHash, final  URL url) {
		final Credential credential = persistence.putContext()
                .putCredential(
                        username,
                        passwordHash
                );
		final Set<String> roles = getRoles(credential.getDefaultManager(), username);
    	final TokenBuilder builder = new TokenBuilder(issuer, roles);
    	final long time = Instant.now().getEpochSecond();
        final Token jwt = builder
        		.expire(jwtExpTimeUnit, jwtExpDuration)
        		.generator(generator)
        		.time(time)
        		.url(url)
        		.userName(username)
        		.build();
        credential.putSession(jwt.getTokenID());
        identityStore.putToken(jwt);
        return jwt.getTokenID();
	}

	@PermitAll
	@Override
	public String loginOneTimePassword(final String oneTimePassword) {
		final Token token = identityStore.removeToken(oneTimePassword);
		if(token != null) {
			return token.getRawToken();
		}
		throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()); 
	}
}
