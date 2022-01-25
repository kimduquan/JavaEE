package epf.security;

import java.io.Serializable;
import java.net.URL;
import java.security.PrivateKey;
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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.persistence.Entities;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.naming.Naming;
import epf.security.client.jwt.JWT;
import epf.security.internal.TokenBuilder;
import epf.security.schema.Principal;
import epf.security.schema.Token;
import epf.util.MapUtil;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;

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
    @Inject
    private transient Tracer tracer;
    
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
     * @param name
     * @return
     */
    protected Map<String, String> buildClaims(final Client client, final String name){
    	final Principal principal = Entities.find(client, Principal.class, epf.security.schema.Security.SCHEMA, epf.security.schema.Security.PRINCIPAL, name);
    	return principal.getClaims();
    }
    
    /**
     * @param client
     * @param username
     * @param rawToken
     * @return
     * @throws Exception
     */
    protected Token buildToken(final Client client, final Token token, final String rawToken) throws Exception {
    	final Map<String, String> claims = buildClaims(client, token.getName());
		token.setClaims(claims);
		claims.put(JWT.TOKEN_CLAIM, rawToken);
		final TokenBuilder builder = new TokenBuilder(token, privateKey);
		return builder.build();
    }
    
    @PermitAll
    @Override
    public String login(
            final String username,
            final String passwordHash,
            final URL url,
            final HttpHeaders headers) throws Exception {
    	try(Client securityClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		final String rawToken = epf.security.client.Security.login(securityClient, username, passwordHash, url);
    		securityClient.authorization(rawToken);
        	final Token token = epf.security.client.Security.authenticate(securityClient);
    		try(Client persistenceClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_URL))){
    			persistenceClient.authorization(rawToken);
    			final Token newToken = buildToken(persistenceClient, token, rawToken);
    			final Map<String, Object> fields = MapUtil.of(Fields.EVENT, "security.login");
    			fields.put("credential.caller", username);
    			tracer.activeSpan().log(fields);
        		login.fire(newToken);
    			return newToken.getRawToken();
    		}
    	}
    }
    
    @Override
    public String logOut() throws Exception {
    	final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	final String rawToken = jwt.getClaim(JWT.TOKEN_CLAIM);
    	try(Client client = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		client.authorization(rawToken);
    		final String name = epf.security.client.Security.logOut(client);
    		final Map<String, Object> fields = MapUtil.of(Fields.EVENT, "security.logout");
    		fields.put("principal.name", name);
			tracer.activeSpan().log(fields);
    		return name;
    	}
    }
    
    @Override
    public Token authenticate() throws Exception {
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
    }

	@Override
	public void update(final String password) throws Exception {
		final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	final String rawToken = jwt.getClaim(JWT.TOKEN_CLAIM);
    	try(Client securityClient = clientUtil.newClient(ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_SECURITY_URL))){
    		securityClient.authorization(rawToken);
    		epf.security.client.Security.update(securityClient, password);
    		final Map<String, Object> fields = MapUtil.of(Fields.EVENT, "security.setPassword");
    		fields.put("principal.name", jwt.getName());
			tracer.activeSpan().log(fields);
    	}
	}

	@Override
	public String revoke(final HttpHeaders headers) throws Exception {
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
    			final Map<String, Object> fields = MapUtil.of(Fields.EVENT, "security.revoke");
        		fields.put("principal.name", jwt.getName());
    			tracer.activeSpan().log(fields);
        		return newToken.getRawToken();
    		}
    	}
	}
}
