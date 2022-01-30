/**
 * 
 */
package epf.schedule.shell;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Shell implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-shell-schedule");
	}
}
