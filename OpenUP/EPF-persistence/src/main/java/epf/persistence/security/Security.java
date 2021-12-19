/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.security;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
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
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.jwt.config.Names;
import epf.naming.Naming;
import epf.persistence.internal.context.Application;
import epf.persistence.internal.context.Credential;
import epf.persistence.internal.context.Session;
import epf.persistence.security.internal.CredentialUtil;
import epf.persistence.security.internal.SecurityUtil;
import epf.persistence.security.internal.SessionUtil;
import epf.persistence.security.otp.OTPIdentityStore;
import epf.security.client.SecurityInterface;
import epf.security.client.jwt.JWT;
import epf.security.client.jwt.TokenUtil;
import epf.security.schema.Token;
import epf.util.EPFException;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.SECURITY)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@RequestScoped
public class Security implements epf.security.client.Security, epf.security.client.otp.OTPSecurity, Serializable, SecurityInterface {
    
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private static final Logger LOGGER = LogManager.getLogger(Security.class.getName());
    
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
    private transient OTPIdentityStore oTPIdentityStore;
    
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
    	token.setGroups(new HashSet<>(Arrays.asList(Naming.Security.DEFAULT_ROLE)));
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
    	try {
			final Token newToken = builder.build();
			defaultCredential.putSession(newToken.getTokenID());
	        return newToken.getRawToken();
		} 
        catch (Exception e) {
			throw new EPFException(e);
		}
    }
    
    @Override
    public String logOut() {
    	final Session defaultSession = SessionUtil.removeSession(context.getUserPrincipal(), persistence.getDefaultContext());
		defaultSession.close();
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
		final JsonWebToken jsonWebToken = (JsonWebToken) context.getUserPrincipal();
		try {
			final Token token = buildToken(jsonWebToken);
			final TokenBuilder builder = new TokenBuilder(token, privateKey);
			final Token newToken = builder.build();
			final Credential defaultCredential = CredentialUtil.getCredential(context.getUserPrincipal(), persistence.getDefaultContext());
			defaultCredential.putSession(newToken.getTokenID());
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
		try {
			final Token newToken = builder.build();
			defaultCredential.putSession(newToken.getTokenID());
	        oTPIdentityStore.putToken(newToken);
	        return newToken.getTokenID();
		}
		catch(Exception ex) {
			throw new EPFException(ex);
		}
	}

	@PermitAll
	@Override
	public String authenticateOneTime(final String oneTimePassword) {
		final Token token = oTPIdentityStore.removeToken(oneTimePassword);
		if(token != null) {
			return token.getRawToken();
		}
		throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()); 
	}

	@ActivateRequestContext
	@Override
	public Token login(final String username, final String passwordHash, final String url) throws RemoteException {
		final Credential defaultCredential = persistence.getDefaultContext().putCredential(username, passwordHash);
    	try {
        	final Token token = buildToken(username, new URL(url));
        	final TokenBuilder builder = new TokenBuilder(token, privateKey);
			final Token newToken = builder.build();
			defaultCredential.putSession(newToken.getTokenID());
	        return newToken;
		} 
        catch (Exception e) {
			throw new EPFException(e);
		}
	}

	@ActivateRequestContext
	@Override
	public Token authenticate(final Token token) throws RemoteException {
		if(SessionUtil.getSession(token.getName(), persistence.getDefaultContext(), token.getTokenID()) != null){
    		return token;
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
	}

	@ActivateRequestContext
	@Override
	public String logout(final Token token) throws RemoteException {
		final Session defaultSession = SessionUtil.removeSession(context.getUserPrincipal(), persistence.getDefaultContext());
		defaultSession.close();
		return token.getName();
	}
}
