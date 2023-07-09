package epf.gateway;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
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
	 * @return
	 */
	public boolean authenticate(final JsonWebToken jwt) {
		return !cache.containsKey(jwt.getTokenID());
	}

	@Override
	public HealthCheckResponse call() {
		if(client != null && cache != null && !cache.isClosed()) {
			return HealthCheckResponse.up("EPF-gateway-security");
		}
		return HealthCheckResponse.down("EPF-gateway-security");
	}
}
