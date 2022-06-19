package epf.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;
import epf.security.util.Credential;
import epf.security.util.IdentityStore;
import epf.security.util.PrincipalStore;

/**
 * 
 */
@Path(Naming.SECURITY)
@RolesAllowed(Naming.EPF)
@ApplicationScoped
public class Registraction implements epf.security.client.Registration {
	
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

	@Override
	public Response createPrincipal(final SecurityContext context) throws Exception {
		final JsonWebToken jwt = (JsonWebToken) context.getUserPrincipal();
		final String password_hash = jwt.getClaim(Naming.Security.Credential.PASSWORD_HASH);
		final Password password = new Password(password_hash.toCharArray());
		final Optional<String> tenant = jwt.claim(Naming.Management.TENANT);
		final Credential credential = new Credential(tenant.orElse(null), jwt.getName(), password);
		final Map<String, Object> claims = new HashMap<>();
		claims.put(Naming.Security.Claims.FIRST_NAME, jwt.getClaim(Naming.Security.Claims.FIRST_NAME));
		claims.put(Naming.Security.Claims.LAST_NAME, jwt.getClaim(Naming.Security.Claims.LAST_NAME));
		claims.put(Naming.Security.Claims.EMAIL, jwt.getClaim(Naming.Security.Claims.EMAIL));
		jwt.claim(Naming.Management.TENANT).ifPresent(tenantClaim -> claims.put(Naming.Management.TENANT, tenantClaim));
		final CallerPrincipal principal = identityStore.authenticate(credential).toCompletableFuture().get();
		if(principal != null) {
			identityStore.setCallerGroup(principal, Naming.Security.DEFAULT_ROLE).toCompletableFuture().get();
			principalStore.putCaller(principal).toCompletableFuture().get();
			principalStore.setCallerClaims(principal, claims).toCompletableFuture().get();
			}
		return Response.ok().build();
	}
}
