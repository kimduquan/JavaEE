package epf.cache.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Provider implements HealthCheck {
	
	/**
	 * 
	 */
	private transient final Map<String, Manager> managers = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Manager manager = new Manager();
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		manager.createCache(Naming.QUERY);
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("epf-cache-provider");
	}

	/**
	 * @param tenant
	 * @return
	 */
	public Manager getManager(final String tenant) {
		if(tenant != null) {
			return managers.get(tenant);
		}
		return manager;
	}
}
