/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.security;

import java.io.Serializable;
import java.net.URL;
import java.security.PrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.jwt.config.Names;
import epf.client.EPFException;
import epf.client.security.Token;
import epf.client.security.jwt.JWT;
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
    private transient ManagedExecutor executor;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = JWT.PRIVATE_KEY)
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
    @ConfigProperty(name = JWT.EXPIRE_DURATION)
    private transient Long expireAmount;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = JWT.EXPIRE_TIMEUNIT)
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
    	final Credential defaultCredential = persistence.getDefaultContext().putCredential(username, passwordHash);
    	final Token token = buildToken(username, url);
    	final TokenBuilder builder = new TokenBuilder(token, privateKey);
    	final Collection<epf.persistence.context.Context> contexts = persistence.getContexts();
    	final List<Future<Credential>> credentials = new ArrayList<>();
    	for(epf.persistence.context.Context context : contexts) {
    		credentials.add(executor.submit(() -> context.putCredential(username, passwordHash)));
    	}
    	try {
			final Token newToken = builder.build();
			defaultCredential.putSession(newToken.getTokenID());
	        for(Future<Credential> credential : credentials) {
		        credential.get().putSession(newToken.getTokenID());
	        }
	        return newToken.getRawToken();
		} 
        catch (ExecutionException e) {
        	throw new EPFException(e.getCause());
		} 
		catch (Exception e) {
			throw new EPFException(e);
		}
    }
    
    @Override
    public String logOut() {
    	final Session defaultSession = SessionUtil.removeSession(context.getUserPrincipal(), persistence.getDefaultContext());
		defaultSession.close();
    	final List<Session> sessions = SessionUtil.removeSessions(context, persistence);
    	for(Session session : sessions){
            session.close();
        }
    	return context.getUserPrincipal().getName();
    }
    
    @Override
    public Token authenticate() {
    	if(SessionUtil.getSession(context.getUserPrincipal(), persistence.getDefaultContext()) != null){
    		final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    		final Token token = TokenUtil.from(jwt);
    		token.setClaims(TokenUtil.getClaims(jwt));
    		token.setRawToken(null);
    		return token;
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    }
    
    @Override
	public void update(final String password) {
		final Credential credential = CredentialUtil.getCredential(context.getUserPrincipal(), persistence.getDefaultContext());
		service.setUserPassword(credential.getDefaultManager(), context.getUserPrincipal().getName(), password.toCharArray());
	}

	@Override
	public String revoke() {
		final Session defaultSession = SessionUtil.removeSession(context.getUserPrincipal(), persistence.getDefaultContext());
		defaultSession.close();
		final List<Session> sessions = SessionUtil.removeSessions(context, persistence);
		for(Session session : sessions) {
			session.close();
		}
		final JsonWebToken jsonWebToken = (JsonWebToken) context.getUserPrincipal();
		try {
			final Token token = buildToken(jsonWebToken);
			final TokenBuilder builder = new TokenBuilder(token, privateKey);
			final Token newToken = builder.build();
			final Credential defaultCredential = CredentialUtil.getCredential(context.getUserPrincipal(), persistence.getDefaultContext());
			defaultCredential.putSession(newToken.getTokenID());
			for(Credential credential : CredentialUtil.getCredentials(context, persistence)) {
				credential.putSession(newToken.getTokenID());
			}
	        return newToken.getRawToken();
		} 
		catch (Exception e) {
			throw new EPFException(e);
		}
	}

	@PermitAll
	@Override
	public String loginOneTime(final String username, final String passwordHash, final  URL url) {
		final Credential defaultCredential = persistence.getDefaultContext().putCredential(username, passwordHash);
    	final Token token = buildToken(username, url);
    	final TokenBuilder builder = new TokenBuilder(token, privateKey);
    	final Collection<epf.persistence.context.Context> contexts = persistence.getContexts();
    	final List<Future<Credential>> credentials = new ArrayList<>();
    	for(epf.persistence.context.Context context : contexts) {
    		credentials.add(executor.submit(() -> context.putCredential(username, passwordHash)));
    	}
		try {
			final Token newToken = builder.build();
			defaultCredential.putSession(newToken.getTokenID());
			for(Future<Credential> credential : credentials) {
		        credential.get().putSession(newToken.getTokenID());
			}
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
