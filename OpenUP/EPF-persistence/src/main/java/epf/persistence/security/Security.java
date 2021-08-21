/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.security;

import java.io.Serializable;
import java.net.URL;
import java.security.Principal;
import java.security.PrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
import epf.client.security.Token;
import epf.client.security.jwt.JWTConfig;
import epf.client.security.jwt.TokenUtil;
import epf.persistence.context.Application;
import epf.persistence.context.Credential;
import epf.persistence.context.Session;
import epf.util.logging.Logging;
import epf.util.security.KeyUtil;

/**
 *
 * @author FOXCONN
 */
@Path("security")
@RolesAllowed(epf.client.security.Security.DEFAULT_ROLE)
@RequestScoped
public class Security implements epf.client.security.Security, epf.client.security.otp.OTPSecurity, Serializable {
    
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private static final Logger LOGGER = Logging.getLogger(Security.class.getName());
    
    /**
     * 
     */
    private transient PrivateKey privateKey;
    
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
    @ConfigProperty(name = JWTConfig.PRIVATE_KEY)
    private transient String privateKeyText;
    
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
    private transient Long expireAmount;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = JWTConfig.EXPIRE_TIMEUNIT)
    private transient ChronoUnit expireTimeUnit;
    
    /**
     * 
     */
    @Inject
    private transient SecurityUtil service;
    
    /**
     * 
     */
    @Context 
    private transient SecurityContext context;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct(){
        try {
            privateKey = KeyUtil.generatePrivate("RSA", privateKeyText);
        } 
        catch (Exception ex) {
            LOGGER.throwing(getClass().getName(), "postConstruct", ex);
        }
    }
    
    /**
     * @param username
     * @param url
     * @return
     */
    protected Token buildToken(final String username, final URL url) {
    	final long now = Instant.now().getEpochSecond();
    	final Token token = new Token();
    	token.setAudience(new HashSet<>(Arrays.asList(String.format(AUDIENCE_FORMAT, url.getProtocol(), url.getHost(), url.getPort()))));
    	token.setClaims(new HashMap<>());
    	token.setExpirationTime(now + Duration.of(expireAmount, expireTimeUnit).getSeconds());
    	token.setGroups(new HashSet<>(Arrays.asList(DEFAULT_ROLE)));
    	token.setIssuedAtTime(now);
    	token.setIssuer(issuer);
    	token.setName(username);
    	token.setSubject(username);
    	return token;
    }
    
    /**
     * @param jsonWebToken
     * @return
     */
    protected Token buildToken(final JsonWebToken jsonWebToken) {
    	final long now = Instant.now().getEpochSecond();
		final Token token = TokenUtil.from(jsonWebToken);
		token.setClaims(TokenUtil.getClaims(jsonWebToken));
		token.setExpirationTime(now + Duration.of(expireAmount, expireTimeUnit).getSeconds());
		token.setIssuedAtTime(now);
		return token;
    }
    
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
    	final Token token = buildToken(username, url);
    	final TokenBuilder builder = new TokenBuilder(token, privateKey);
    	try {
			final Token newToken = builder.build();
	        credential.putSession(newToken.getTokenID());
	        return newToken.getRawToken();
		} 
		catch (Exception e) {
			throw new EPFException(e);
		}
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
    		final Token token = TokenUtil.from(jwt);
    		token.setClaims(TokenUtil.getClaims(jwt));
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

	@Override
	public void update(final String password) {
		final Credential credential = getCredential(context, persistence);
		service.setUserPassword(credential.getDefaultManager(), context.getUserPrincipal().getName(), password.toCharArray());
	}

	@Override
	public String revoke() {
		final Session session = removeSession(context, persistence);
		if(session != null) {
			session.close();
			if(context.getUserPrincipal() instanceof JsonWebToken) {
				final JsonWebToken jsonWebToken = (JsonWebToken) context.getUserPrincipal();
				try {
					final Token token = buildToken(jsonWebToken);
					final TokenBuilder builder = new TokenBuilder(token, privateKey);
					final Token newToken = builder.build();
					final Credential credential = getCredential(context, persistence);
					credential.putSession(newToken.getTokenID());
			        return newToken.getRawToken();
				} 
				catch (Exception e) {
					throw new EPFException(e);
				}
			}
		}
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
	}

	@PermitAll
	@Override
	public String loginOneTime(final String username, final String passwordHash, final  URL url) {
		final Credential credential = persistence.putContext()
                .putCredential(
                        username,
                        passwordHash
                );
		final Token token = buildToken(username, url);
		final TokenBuilder builder = new TokenBuilder(token, privateKey);
		try {
			final Token newToken = builder.build();
	        credential.putSession(newToken.getTokenID());
	        identityStore.putToken(newToken);
	        return newToken.getTokenID();
		}
		catch(Exception ex) {
			throw new EPFException(ex);
		}
	}

	@PermitAll
	@Override
	public String authenticateOneTime(final String oneTimePassword) {
		final Token token = identityStore.removeToken(oneTimePassword);
		if(token != null) {
			return token.getRawToken();
		}
		throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()); 
	}
}
