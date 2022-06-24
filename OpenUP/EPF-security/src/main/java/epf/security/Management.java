package epf.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;
import epf.security.internal.sql.NativeQueries;
import epf.security.internal.store.TenantPersistence;
import epf.security.internal.token.TokenBuilder;
import epf.security.schema.Token;
import epf.security.util.Credential;
import epf.security.util.CredentialUtil;
import epf.security.util.IdentityStore;
import epf.security.util.PrincipalStore;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;

/**
 * 
 */
@Path(Naming.SECURITY)
@RolesAllowed(Naming.EPF)
@ApplicationScoped
public class Management implements epf.security.client.Management {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Management.class.getName());
	
	/**
     * 
     */
    private transient PrivateKey privateKey;
    
    /**
     * 
     */
    private transient PublicKey publicKey;
	
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
    @ConfigProperty(name = Naming.Security.JWT.VERIFY_KEY)
    transient String publicKeyText;
	
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
	@PersistenceContext(unitName = IdentityStore.SECURITY_SCHEMA_UNIT_NAME)
	transient EntityManager manager;
	
	/**
	 * 
	 */
	@PersistenceContext(unitName = IdentityStore.SECURITY_UNIT_NAME)
	transient EntityManager principalManager;
	
	/**
	 *
	 */
	@Inject
	transient TenantPersistence persistence;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct() {
    	try {
			privateKey = KeyUtil.generatePrivate("RSA", privateKeyText, Base64.getDecoder(), "UTF-8");
	        publicKey = KeyUtil.generatePublic("RSA", publicKeyText, Base64.getDecoder(), "UTF-8");
		}
    	catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[Management.postConstruct]", e);
		}
    }

    @PermitAll
	@Override
	public Response createCredential(
			final String tenant,
			final String email, 
			final String password, 
			final String firstName, 
			final String lastName,
			final List<String> forwardedHost,
            final List<String> forwardedPort,
            final List<String> forwardedProto) throws Exception {
		final Credential credential = new Credential(tenant, email, new Password(password));
		identityStore.putCredential(credential).toCompletableFuture().get();
		final Set<String> audience = TokenBuilder.buildAudience(null, forwardedHost, forwardedPort, forwardedProto, Optional.ofNullable(tenant));
		final Set<String> groups = new HashSet<>();
		groups.add(Naming.EPF);
		final Map<String, Object> claims = new HashMap<>();
		if(tenant != null) {
			claims.put(Naming.Management.TENANT, tenant);
		}
		claims.put(Naming.Security.Claims.FIRST_NAME, firstName);
		claims.put(Naming.Security.Claims.LAST_NAME, lastName);
		claims.put(Naming.Security.Claims.EMAIL, email);
		claims.put(Naming.Security.Credential.PASSWORD_HASH, CredentialUtil.encryptPassword(email, password));
		
		final Token newToken = new Token();
		newToken.setAudience(audience);
		newToken.setClaims(claims);
		newToken.setIssuedAtTime(Instant.now().getEpochSecond());
		newToken.setExpirationTime(newToken.getIssuedAtTime() + Duration.ofDays(3).toSeconds());
		newToken.setGroups(groups);
		newToken.setIssuer(Naming.EPF);
		newToken.setName(email);
		newToken.setSubject(email);
		
		final TokenBuilder builder = new TokenBuilder(newToken, privateKey, publicKey);
		return Response.ok(builder.build().getRawToken()).build();
	}

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
		final CallerPrincipal principal = identityStore.authenticate(credential).toCompletableFuture().get();
		if(principal != null) {
			identityStore.setCallerGroup(principal, Naming.Security.DEFAULT_ROLE).toCompletableFuture().get();
			principalStore.putCaller(principal).toCompletableFuture().get();
			principalStore.setCallerClaims(principal, claims).toCompletableFuture().get();
			return Response.ok().build();
		}
		throw new BadRequestException();
	}

	@PermitAll
	@Override
	public Response resetPassword(
			final String tenant,
			final String email,
			final List<String> forwardedHost,
            final List<String> forwardedPort,
            final List<String> forwardedProto) {
		EntityManager entityManager = manager;
		if(tenant != null) {
			entityManager = persistence.createManager(tenant);
		}
		final Integer count = (Integer) entityManager.createNativeQuery(NativeQueries.CHECK_USER).setParameter(1, email).getSingleResult();
		if(count > 0) {
			final Set<String> audience = TokenBuilder.buildAudience(null, forwardedHost, forwardedPort, forwardedProto, Optional.ofNullable(tenant));
			final Set<String> groups = new HashSet<>();
			groups.add(Naming.EPF);
			final Map<String, Object> claims = new HashMap<>();
			if(tenant != null) {
				claims.put(Naming.Management.TENANT, tenant);
			}
			claims.put(Naming.Security.Claims.EMAIL, email);
			
			final Token newToken = new Token();
			newToken.setAudience(audience);
			newToken.setClaims(claims);
			newToken.setIssuedAtTime(Instant.now().getEpochSecond());
			newToken.setExpirationTime(newToken.getIssuedAtTime() + Duration.ofDays(3).toSeconds());
			newToken.setGroups(groups);
			newToken.setIssuer(Naming.EPF);
			newToken.setName(email);
			newToken.setSubject(email);
			
			final TokenBuilder builder = new TokenBuilder(newToken, privateKey, publicKey);
			return Response.ok(builder.build().getRawToken()).build();
		}
		throw new BadRequestException();
	}

	@Override
	public Response setPassword(final String password, final SecurityContext context) throws Exception {
		final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
		final Optional<String> tenant = Optional.ofNullable(jwt.getClaim(Naming.Management.TENANT));
		EntityManager entityManager = manager;
		if(tenant.isPresent()) {
			entityManager = persistence.createManager(tenant.get());
		}
		entityManager.createNativeQuery(String.format(NativeQueries.SET_USER__PASSWORD, jwt.getName())).setParameter(1, password).executeUpdate();
		return Response.ok().build();
	}
}