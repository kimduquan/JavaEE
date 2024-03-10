package epf.event;

import java.util.Map;
import javax.cache.Cache;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import epf.naming.Naming.Event.Internal;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class EventCache implements HealthCheck {
	
	/**
	 * 
	 */
	private transient HazelcastInstance instance;
	
	/**
	 * 
	 */
	private transient Cache<String, Map<String, Object>> eventCache;
	
	/**
	 * 
	 */
	@PostConstruct()
	protected void postConstruct() {
		instance = HazelcastClient.getOrCreateHazelcastClient();
		eventCache = instance.getCacheManager().getCache(Internal.EPF_EVENT_CACHE);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		eventCache.close();
		instance.shutdown();
	}

	@Override
	public HealthCheckResponse call() {
		if(eventCache != null) {
			return HealthCheckResponse.up(Internal.EPF_EVENT_CACHE);
		}
		return HealthCheckResponse.down(Internal.EPF_EVENT_CACHE);
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void put(final String key, final Map<String, Object> value) {
		eventCache.put(key, value);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public Map<String, Object> remove(final String key) {
		return eventCache.getAndRemove(key);
	}
}
