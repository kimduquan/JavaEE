package epf.gateway;

import java.util.Objects;
import java.util.Optional;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.util.RequestUtil;
import epf.naming.Naming;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class Security implements HealthCheck {
	
	/**
	 * 
	 */
	private transient Cache<String, String> cache;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final MutableConfiguration<String, String> config = new MutableConfiguration<>();
		final CacheManager manager = Caching.getCachingProvider().getCacheManager();
		cache = manager.getCache(Naming.Security.Internal.SECURITY_CACHE);
		if(cache == null) {
			cache = manager.createCache(Naming.Security.Internal.SECURITY_CACHE, config);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		cache.close();
	}
	
	/**
	 * @param jwt
	 * @param uriInfo
	 * @return
	 */
	public boolean authenticate(final JsonWebToken jwt, final UriInfo uriInfo) {
		Objects.requireNonNull(jwt, "JsonWebToken");
		Objects.requireNonNull(uriInfo, "UriInfo");
		if(cache.containsKey(jwt.getTokenID())) {
    		return false;
    	}
		final Optional<String> tenant = RequestUtil.getTenant(uriInfo);
		final Optional<String> tenantClaim = jwt.claim(Naming.Management.TENANT);
		if(tenant.isPresent() && tenantClaim.isPresent()) {
			return tenant.get().equals(tenantClaim.get());
		}
		if(!tenant.isPresent() && !tenantClaim.isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public HealthCheckResponse call() {
		if(cache != null && !cache.isClosed()) {
			return HealthCheckResponse.up("EPF-gateway-security");
		}
		return HealthCheckResponse.down("EPF-gateway-security");
	}
}
