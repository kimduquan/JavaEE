package epf.security;

import java.io.Serializable;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.jwt.config.Names;
import epf.naming.Naming;
import epf.security.client.jwt.TokenUtil;
import epf.security.internal.Session;
import epf.security.internal.store.OTPSessionStore;
import epf.security.internal.store.SessionStore;
import epf.security.internal.token.TokenBuilder;
import epf.security.schema.Token;
import epf.security.util.Credential;
import epf.security.util.CredentialUtil;
import epf.security.util.IdentityStore;
import epf.security.util.JPAPrincipal;
import epf.security.util.PrincipalStore;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.SECURITY)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@ApplicationScoped
public class Security implements epf.security.client.Security, epf.security.client.otp.OTPSecurity, Serializable {
    
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
    private transient PublicKey encryptKey;
    
    /**
     * 
     */
    @Inject
    transient OTPSessionStore otpSessionStore;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = Naming.Security.JWT.ISSUE_KEY)
    transient String privateKeyText;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = Names.ISSUER)
    transient String issuer;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = Naming.Security.JWT.EXPIRE_DURATION)
    transient String expireDuration;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = Naming.Security.JWT.ENCRYPT_KEY)
    transient String encryptKeyText;
    
    /**
     * 
     */
    @Inject
    transient IdentityStore identityStore;
    
    /**
     * 
     */
    @Inject
    transient PrincipalStore principalStore;
    
    /**
     * 
     */
    @Inject
    transient SessionStore sessionStore;
    
    /**
     * 
     */
    @Inject
    transient Event<Token> event;
    
    /**
     * 
     */
    @PostConstruct
    void postConstruct(){
        try {
            privateKey = KeyUtil.generatePrivate("RSA", privateKeyText, Base64.getDecoder(), "UTF-8");
            encryptKey = KeyUtil.generatePublic("RSA", encryptKeyText, Base64.getDecoder(), "UTF-8");
        } 
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "postConstruct", ex);
        }
    }
    
    /**
     * @param username
     * @param url
     * @return
     */
    Token newToken(final String name, final Set<String> groups, final Set<String> audience, final Map<String, Object> claims) {
    	final long now = Instant.now().getEpochSecond();
    	final Token token = new Token();
    	token.setAudience(audience);
    	token.setClaims(claims);
    	token.setExpirationTime(now + Duration.parse(expireDuration).getSeconds());
    	token.setGroups(groups);
    	token.setIssuedAtTime(now);
    	token.setIssuer(issuer);
    	token.setName(name);
    	token.setSubject(name);
    	return token;
    }
    
    /**
     * @param jsonWebToken
     * @return
     */
    Token newToken(final JsonWebToken jsonWebToken, final Set<String> groups, final Set<String> audience, final Map<String, Object> claims) {
    	final long now = Instant.now().getEpochSecond();
		final Token token = TokenUtil.from(jsonWebToken);
		token.setAudience(audience);
		token.setClaims(claims);
		token.setExpirationTime(now + Duration.parse(expireDuration).getSeconds());
		token.setIssuedAtTime(now);
		token.setGroups(groups);
		return token;
    }
    
    /**
     * @return
     */
    Set<String> buildAudience(
            final URL url,
            final List<String> forwardedHost,
            final List<String> forwardedPort,
            final List<String> forwardedProto){
    	final Set<String> audience = new HashSet<>();
    	if(url != null) {
    		audience.add(String.format(AUDIENCE_FORMAT, url.getProtocol(), url.getHost(), url.getPort()));
    	}
		if(forwardedHost != null && forwardedPort != null && forwardedProto != null) {
			for(int i = 0; i < forwardedHost.size(); i++) {
				audience.add(String.format(AUDIENCE_FORMAT, forwardedProto.get(i), forwardedHost.get(i), forwardedPort.get(i)));
			}
		}
		return audience;
    }
    
    /**
     * @param claims
     * @param ternant
     * @return
     */
    Map<String, Object> buildClaims(final Map<String, Object> claims, final String ternant){
    	final Map<String, Object> newClaims = new HashMap<>(claims);
    	if(ternant != null) {
        	newClaims.put(Naming.Management.TERNANT, ternant);
    	}
    	return newClaims;
    }
    
    @PermitAll
    @Override
    public CompletionStage<String> login(
            final String username,
            final String passwordText,
            final URL url,
            final String ternant,
            final List<String> forwardedHost,
            final List<String> forwardedPort,
            final List<String> forwardedProto) throws Exception {
    	final Credential credential = CredentialUtil.newCredential(ternant, username, passwordText);
    	return identityStore.validate(credential)
    			.thenApply(result -> {
    				if(Status.VALID.equals(result.getStatus())){
    					return (JPAPrincipal)result.getCallerPrincipal();
    					}
    					throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    					}
    			)
    			.thenCompose(principal -> identityStore.getCallerGroups(principal)
    					.thenCombine(
								principalStore.getCallerClaims(principal), 
								(groups, claims) -> {
									final Set<String> audience = buildAudience(url, forwardedHost, forwardedPort, forwardedProto);
									final Map<String, Object> newClaims = buildClaims(claims, ternant);
									final Token token = newToken(principal.getName(), groups, audience, newClaims);
									final TokenBuilder builder = new TokenBuilder(token, privateKey, encryptKey);
									final Token newToken = builder.build();
									sessionStore.putSession(principal, newToken, credential);
									return newToken;
									}
								)
    			)
    			.thenApply(token -> { 
    				event.fire(token);
    				return token.getRawToken();
    				}
    			);
    }
    
    @Override
    public String logOut(final SecurityContext context) throws Exception {
    	final Session session = sessionStore.removeSession(context).orElseThrow(ForbiddenException::new);
    	return session.getToken().getName();
    }
    
    @Override
    public Token authenticate(final SecurityContext context) {
    	sessionStore.getSession(context).orElseThrow(() -> new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()));
    	final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    	final Token token = TokenUtil.from(jwt);
		token.setClaims(TokenUtil.getClaims(jwt));
		token.setRawToken(null);
		return token;
    }
    
    @Override
	public CompletionStage<Void> update(final String password, final SecurityContext context) {
    	final Session session = sessionStore.getSession(context).orElseThrow(ForbiddenException::new);
    	return principalStore.setCallerPassword(session.getPrincipal(), new Password(password));
	}

	@Override
	public CompletionStage<String> revoke( 
            final SecurityContext context,
			final List<String> forwardedHost,
            final List<String> forwardedPort,
            final List<String> forwardedProto) throws Exception {
		final Session session = sessionStore.removeSession(context).orElseThrow(ForbiddenException::new);
		final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
		final Set<String> audience = buildAudience(null, forwardedHost, forwardedPort, forwardedProto);
		audience.addAll(jwt.getAudience());
		return identityStore.getCallerGroups(session.getPrincipal())
				.thenCombine(principalStore.getCallerClaims(session.getPrincipal()), (groups, claims) -> newToken(jwt, groups, audience, claims))
						.thenApply(token -> new TokenBuilder(token, privateKey, encryptKey))
						.thenApply(builder -> builder.build())
						.thenApply(newToken -> {
							sessionStore.putSession(session.getPrincipal(), newToken, session.getCredential());
							return newToken;
						})
						.thenApply(token -> {
							event.fire(token);
							return token.getRawToken();
						});
	}

	@PermitAll
	@Override
	public CompletionStage<String> loginOneTime(
			final String username, 
			final String passwordText, 
			final  URL url,
			final String ternant) throws Exception {
		final Credential credential = CredentialUtil.newCredential(ternant, username, passwordText);
    	return identityStore.validate(credential)
    			.thenApply(result -> {
    				if(Status.VALID.equals(result.getStatus())) {
        				return (JPAPrincipal)result.getCallerPrincipal();
    					}
					throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    				})
    			.thenApply(principal -> otpSessionStore.putSession(principal, credential));
	}

	@PermitAll
	@Override
	public CompletionStage<String> authenticateOneTime(
			final String oneTimePassword,
			final URL url,
			final List<String> forwardedHost,
            final List<String> forwardedPort,
            final List<String> forwardedProto) {
		final Session session = otpSessionStore.removeSession(oneTimePassword).orElseThrow(() -> new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()));
		final Set<String> audience = buildAudience(url, forwardedHost, forwardedPort, forwardedProto);
		return identityStore.getCallerGroups(session.getPrincipal())
		.thenCombine(principalStore.getCallerClaims(session.getPrincipal()), (groups, claims) -> newToken(session.getPrincipal().getName(), groups, audience, claims))
		.thenApply(token -> new TokenBuilder(token, privateKey, encryptKey))
		.thenApply(builder -> builder.build())
		.thenApply(newToken -> {
			sessionStore.putSession(session.getPrincipal(), newToken, session.getCredential());
			return newToken;
		})
		.thenApply(token -> { 
			event.fire(token);
			return token.getRawToken();
			});
	}
}
