package epf.cache;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Readiness
public class Cache implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up(Naming.Event.Internal.EPF_EVENT_CACHE);
	}
}
