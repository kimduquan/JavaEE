package epf.cache.internal;

import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.cache.Caching;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.client.util.RemoteCachingProviderImpl;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class RemoteProvider implements HealthCheck {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(RemoteProvider.class.getName());

	@Override
	public HealthCheckResponse call() {
		try {
			LocateRegistry.getRegistry().rebind(Naming.CACHE, new RemoteCachingProviderImpl(Caching.getCachingProvider()));
			return HealthCheckResponse.up("epf-cache-remote-provider");
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "RemoteProvider.HealthCheck", e);
			return HealthCheckResponse.down("epf-cache-remote-provider");
		}
	}

}
