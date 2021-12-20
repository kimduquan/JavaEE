/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.security;

import java.io.Serializable;
import java.net.URL;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.util.EPFException;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.naming.Naming;
import epf.security.client.jwt.JWT;
import epf.security.schema.Token;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;
import openup.schema.OpenUP;
import openup.schema.roles.Role;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.SECURITY)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@RequestScoped
public class Security implements epf.security.client.Security, Serializable {
    
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
    @ConfigProperty(name = JWT.PRIVATE_KEY)
    private transient String privateKeyText;
    
    /**
     * 
     */
    @Inject
    private transient ClientUtil clientUtil;
    
    /**
     * 
     */
    @Context 
    private transient SecurityContext context;
    
    /**
     * 
     */
    @Inject
    private transient Event<Token> login;
    
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
     * @param client
     * @param username
     * @param rawToken
     * @return
     * @throws Exception
     */
    protected Token buildToken(final Client client, final Token token, final String rawToken) throws Exception {
    	final Role role = epf.client.persistence.Entities.find(client, Role.class, OpenUP.SCHEMA, OpenUP.ROLE, token.getName());
		final Map<String, String> claims = new HashMap<>(role.getClaims());
		claims.put(JWT.TOKEN_CLAIM, rawToken);
		token.setClaims(claims);
		final TokenBuilder builder = new TokenBuilder(token, privateKey);
		return builder.build();
    }
    
    @PermitAll
    @Override
    public String login(
            final String username,
            final String passwordHash,
            final URL url) {
    	try(Client securityClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		final String rawToken = epf.security.client.Security.login(securityClient, username, passwordHash, url);
    		securityClient.authorization(rawToken);
        	final Token token = epf.security.client.Security.authenticate(securityClient);
    		try(Client persistenceClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_URL))){
    			persistenceClient.authorization(rawToken);
    			final Token newToken = buildToken(persistenceClient, token, rawToken);
        		login.fire(newToken);
    			return newToken.getRawToken();
    		}
    	} 
    	catch (Exception e) {
			throw new EPFException(e);
		}
    }
    
    @Override
    public String logOut() {
    	final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	final String rawToken = jwt.getClaim(JWT.TOKEN_CLAIM);
    	try(Client client = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		client.authorization(rawToken);
    		return epf.security.client.Security.logOut(client);
    	} 
    	catch (Exception e) {
			throw new EPFException(e);
		}
    }
    
    @Override
    public Token authenticate() {
    	final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	final String rawToken = jwt.getClaim(JWT.TOKEN_CLAIM);
    	try(Client securityClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		securityClient.authorization(rawToken);
    		final Token token = epf.security.client.Security.authenticate(securityClient);
    		try(Client persistenceClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_URL))){
    			persistenceClient.authorization(rawToken);
    			final Token newToken = buildToken(persistenceClient, token, rawToken);
    			newToken.setRawToken(null);
    			newToken.getClaims().remove(JWT.TOKEN_CLAIM);
    			return newToken;
    		}
    	} 
    	catch (Exception e) {
			throw new EPFException(e);
		}
    }

	@Override
	public void update(final String password) {
		final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	final String rawToken = jwt.getClaim(JWT.TOKEN_CLAIM);
    	try(Client securityClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		securityClient.authorization(rawToken);
    		epf.security.client.Security.update(securityClient, password);
    	}
    	catch (Exception e) {
			throw new EPFException(e);
		}
	}

	@Override
	public String revoke() {
		final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	final String rawToken = jwt.getClaim(JWT.TOKEN_CLAIM);
    	try(Client securityClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		securityClient.authorization(rawToken);
    		final String newRawToken = epf.security.client.Security.revoke(securityClient);
    		securityClient.authorization(newRawToken);
    		final Token token = epf.security.client.Security.authenticate(securityClient);
    		try(Client persistenceClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_URL))){
    			persistenceClient.authorization(newRawToken);
    			final Token newToken = buildToken(persistenceClient, token, newRawToken);
        		return newToken.getRawToken();
    		}
    	} 
    	catch (Exception e) {
			throw new EPFException(e);
		}
	}
}
