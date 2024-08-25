package epf.security;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Password;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.jwt.config.Names;
import org.jose4j.jwt.JwtClaims;
import epf.naming.Naming;
import epf.security.auth.Provider;
import epf.security.auth.StandardProvider;
import epf.security.auth.util.JwtUtil;
import epf.security.client.jwt.TokenUtil;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.Session;
import epf.security.internal.TokenCache;
import epf.security.internal.store.JPAIdentityStore;
import epf.security.internal.store.JPAPrincipalStore;
import epf.security.internal.store.OTPSessionStore;
import epf.security.internal.token.TokenBuilder;
import epf.security.schema.Token;
import epf.security.util.Credential;
import epf.security.util.CredentialUtil;
import epf.util.config.ConfigUtil;
import epf.util.io.FileUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyStoreUtil;
import epf.util.security.KeyUtil;
import epf.util.security.SecurityUtil;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.SECURITY)
@ApplicationScoped
@Readiness
public class Security implements epf.security.client.Security, epf.security.client.otp.OTPSecurity, Serializable, HealthCheck {
    
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private static transient final Logger LOGGER = LogManager.getLogger(Security.class.getName());
    
    /**
     * 
     */
    private transient PrivateKey privateKey;
    
    /**
     * 
     */
    private transient SecretKey secretKey;
    
	/**
	 *
	 */
	private transient KeyStore trustStore;
	
	/**
	 *
	 */
	private transient ClientBuilder builder;
    
    /**
     *
     */
    private transient final Map<String, Provider> authProviders = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @Inject
    transient OTPSessionStore otpSessionStore;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = Naming.Security.JWT.DECRYPTOR_KEY_LOCATION)
    transient String privateKeyLocation;
    
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
    @ConfigProperty(name = Naming.Security.Auth.CLIENT_SECRET_KEY)
	@Inject
	transient String clientSecretKey;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_PROVIDER)
	@Inject
	transient String googleDiscoveryUrl;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_CLIENT_ID)
	@Inject
	transient String googleClientId;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_CLIENT_SECRET)
	@Inject
	transient String googleClientSecret;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.FACEBOOK_PROVIDER)
	@Inject
	transient String facebookDiscoveryUrl;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.FACEBOOK_CLIENT_ID)
	@Inject
	transient String facebookClientId;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.FACEBOOK_CLIENT_SECRET)
	@Inject
	transient String facebookClientSecret;
    
    /**
     * 
     */
    @Inject
    @Readiness
    transient JPAIdentityStore identityStore;
    
    /**
     * 
     */
    @Inject
    @Readiness
    transient JPAPrincipalStore principalStore;
    
    /**
     * 
     */
    @Inject
    @Readiness
    transient TokenCache tokenCache;
    
    /**
     * 
     */
    @PostConstruct
    void postConstruct() {
        try {
        	final java.nio.file.Path trustStoreFile = ConfigUtil.getPath(Naming.Client.SSL_TRUST_STORE);
        	final String trustStoreType = ConfigUtil.getString(Naming.Client.SSL_TRUST_STORE_TYPE);
        	final char[] trustStorePassword = ConfigUtil.getChars(Naming.Client.SSL_TRUST_STORE_PASSWORD);
        	trustStore = KeyStoreUtil.loadKeyStore(trustStoreType, trustStoreFile, trustStorePassword);
        	builder = ClientBuilder.newBuilder().trustStore(trustStore);
        	privateKey = KeyUtil.decodePrivateKey("RSA", FileUtil.readAllBytes(privateKeyLocation));
        	secretKey = KeyUtil.decodeFromString(clientSecretKey, "AES");
        	final String googleSecret = SecurityUtil.decrypt(googleClientSecret, secretKey);
            authProviders.put(Naming.Security.Auth.GOOGLE, new StandardProvider(new URI(googleDiscoveryUrl), googleSecret.toCharArray(), googleClientId, builder));
            final String facebookSecret = SecurityUtil.decrypt(facebookClientSecret, secretKey);
            authProviders.put(Naming.Security.Auth.FACEBOOK, new StandardProvider(new URI(facebookDiscoveryUrl), facebookSecret.toCharArray(), facebookClientId, builder));
        } 
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "[Security.postConstruct]", ex);
        }
    }
    
    /**
     * @param username
     * @param url
     * @return
     */
    Token newToken(final String name, final Set<String> groups, final Set<String> audience, final Map<String, Object> claims, final String duration) {
    	final long now = Instant.now().getEpochSecond();
    	final Token token = new Token();
    	token.setAudience(audience);
    	token.setClaims(claims);
    	token.setExpirationTime(now + Duration.parse(duration).getSeconds());
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
    Token newToken(final JsonWebToken jsonWebToken, final Set<String> groups, final Set<String> audience, final Map<String, Object> claims, final String duration) {
    	final long now = Instant.now().getEpochSecond();
		final Token token = TokenUtil.from(jsonWebToken);
		token.setAudience(audience);
		token.setClaims(claims);
		token.setExpirationTime(now + Duration.parse(duration).getSeconds());
		token.setIssuedAtTime(now);
		token.setGroups(groups);
		return token;
    }
    
    @PermitAll
    @Override
    public CompletionStage<String> login(
            final String username,
            final String passwordText,
            final URL url,
            final String tenant,
            final List<String> forwardedHost) throws Exception {
    	final Credential credential = CredentialUtil.newCredential(tenant, username, passwordText);
    	return identityStore.authenticate(credential)
    			.thenApply(
    					principal -> {
    						if(principal == null){ 
    							throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()); 
    							}
    						return principal;
    				})
    			.thenCompose(principal -> identityStore.getCallerGroups(principal.getManager(), credential)
    					.thenApply(groups -> {
    						principal.close();
    						return groups;
    						}
    					)
    					.thenCombine(
								principalStore.getCallerClaims(credential), 
								(groups, claims) -> {
									final Set<String> audience = TokenBuilder.buildAudience(url, forwardedHost, credential.getTenant());
									final Map<String, Object> newClaims = TokenBuilder.buildClaims(claims, credential.getTenant());
									final Token token = newToken(principal.getName(), groups, audience, newClaims, expireDuration);
									final TokenBuilder builder = new TokenBuilder(token, privateKey);
									final Token newToken = builder.build();
									return newToken;
									}
								)
    			)
    			.thenApply(token -> token.getRawToken());
    }

    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @Override
    public String logOut(final SecurityContext context) throws Exception {
    	final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	tokenCache.expireToken(jwt);
    	return jwt.getName();
    }
    
    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @Override
    public Token authenticate(final String tenant, final SecurityContext context) {
    	final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	final Token token = TokenUtil.from(jwt);
		token.setClaims(TokenUtil.getClaims(jwt));
		token.setRawToken(null);
		return token;
    }
    
    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @Override
	public CompletionStage<Response> update(final String password, final SecurityContext context) throws Exception {
    	final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
    	return principalStore.setCallerPassword(jwt, new Password(password)).thenApply((v) -> Response.ok().build());
	}

    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @Override
	public CompletionStage<String> revoke( 
            final SecurityContext context,
			final List<String> forwardedHost,
            final String duration) throws Exception {
    	final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
		tokenCache.expireToken(jwt);
		final String tokenDuration = duration != null && !duration.isEmpty() ? duration : expireDuration;
		final Set<String> audience = TokenBuilder.buildAudience(null, forwardedHost, Optional.ofNullable(jwt.getClaim(Naming.Management.TENANT)));
		audience.addAll(jwt.getAudience());
		return identityStore.getCallerGroups(jwt)
				.thenCombine(principalStore.getCallerClaims(jwt), (groups, claims) -> newToken(jwt, groups, audience, claims, tokenDuration))
						.thenApply(token -> new TokenBuilder(token, privateKey))
						.thenApply(builder -> builder.build())
						.thenApply(token -> token.getRawToken());
	}

	@PermitAll
	@Override
	public CompletionStage<String> loginOneTime(
			final String username, 
			final String passwordText,
			final String tenant) throws Exception {
		final Credential credential = CredentialUtil.newCredential(tenant, username, passwordText);
		return identityStore.authenticate(credential)
    			.thenApply(
    					principal -> {
    						if(principal == null){ 
    							throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()); 
    							}
    						return principal;
    				})
    			.thenCompose(principal -> identityStore.getCallerGroups(principal.getManager(), credential)
    					.thenApply(groups -> {
    						principal.close();
    						return groups;
    						}
    					)
    					.thenCombine(
								principalStore.getCallerClaims(credential), 
								(groups, claims) -> otpSessionStore.putSession(credential.getCaller(), groups, claims)
								)
    			);
	}

	@PermitAll
	@Override
	public String authenticateOneTime(
			final String oneTimePassword,
			final URL url,
			final List<String> forwardedHost,
            final String tenant) {
		final Session session = otpSessionStore.removeSession(oneTimePassword).orElseThrow(() -> new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()));
		final Set<String> audience = TokenBuilder.buildAudience(url, forwardedHost, Optional.ofNullable(tenant));
		final Token token = newToken(session.getName(), session.getGroups(), audience, session.getClaims(), expireDuration);
		final TokenBuilder builder = new TokenBuilder(token, privateKey);
		return builder.build().getRawToken();
	}

	@PermitAll
	@Override
	public Response authenticateIDToken(
			final String provider, 
			final String session, 
			final String token,
            final URL url,
			final String tenant,
            final List<String> forwardedHost) throws Exception {
		final Provider authProvider = authProviders.get(provider);
		if(authProvider == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if(!authProvider.validateIDToken(token, session)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		final JwtClaims claims = JwtUtil.decode(token.toCharArray());
		final Set<String> audience = TokenBuilder.buildAudience(url, forwardedHost, Optional.ofNullable(tenant));
		final Set<String> groups = new HashSet<>();
		groups.add(Naming.Security.DEFAULT_ROLE);
		final Map<String, Object> newClaims = new HashMap<>();
		if(tenant != null) {
			newClaims.put(Naming.Management.TENANT, tenant);
		}
		final Token newToken = new Token();
		newToken.setAudience(audience);
		newToken.setClaims(newClaims);
		newToken.setExpirationTime(claims.getExpirationTime().getValue());
		newToken.setGroups(groups);
		newToken.setIssuedAtTime(claims.getIssuedAt().getValue());
		newToken.setIssuer(issuer);
		newToken.setName(claims.getStringClaimValue(Naming.Security.Claims.EMAIL));
		newToken.setSubject(claims.getSubject());
		final TokenBuilder builder = new TokenBuilder(newToken, privateKey);
		return Response.ok(builder.build()).build();
	}
	
	@RolesAllowed(Naming.EPF)
    @Override
	public Response createPrincipal(final SecurityContext context) throws Exception {
		final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
		final String password_hash = jwt.getClaim(Naming.Security.Credential.PASSWORD_HASH);
		final Password password = new Password(password_hash.toCharArray());
		final Optional<String> tenant = Optional.ofNullable(jwt.getClaim(Naming.Management.TENANT));
		final Credential credential = new Credential(tenant.orElse(null), jwt.getName(), password);
		final Map<String, Object> claims = new HashMap<>();
		claims.put(Naming.Security.Claims.FIRST_NAME, jwt.getClaim(Naming.Security.Claims.FIRST_NAME));
		claims.put(Naming.Security.Claims.LAST_NAME, jwt.getClaim(Naming.Security.Claims.LAST_NAME));
		claims.put(Naming.Security.Claims.EMAIL, jwt.getClaim(Naming.Security.Claims.EMAIL));
		tenant.ifPresent(tenantClaim -> claims.put(Naming.Management.TENANT, tenantClaim));
		final JPAPrincipal principal = identityStore.authenticate(credential).toCompletableFuture().get();
		if(principal != null) {
			principalStore.putCaller(principal).toCompletableFuture().get();
			principalStore.setCallerClaims(principal, claims).toCompletableFuture().get();
			principal.close();
			return Response.ok().build();
		}
		throw new BadRequestException();
	}

	@Override
	public HealthCheckResponse call() {
		if(this.privateKey == null) {
			return HealthCheckResponse.down("epf-security-private-key");
		}
		if(this.trustStore == null) {
			return HealthCheckResponse.down("epf-security-trust-store");
		}
		return HealthCheckResponse.up("epf-security");
	}
}
