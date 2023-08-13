package epf.gateway;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import epf.client.util.RequestUtil;
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
	private transient HazelcastInstance client;
	
	/**
	 * 
	 */
	private transient Cache<String, String> cache;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		client = HazelcastClient.newHazelcastClient();
		cache = client.getCacheManager().getCache(Naming.Security.Internal.SECURITY_CACHE);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		cache.close();
		client.shutdown();
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
		if(client != null && cache != null && !cache.isClosed()) {
			return HealthCheckResponse.up("EPF-gateway-security");
		}
		return HealthCheckResponse.down("EPF-gateway-security");
	}
}
