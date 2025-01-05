package epf.lang;

import epf.lang.ollama.Ollama;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Readiness
public class Persistence implements HealthCheck {
	
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.EMBED_LANGUAGE_MODEL)
	String embedModel;
	
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.CACHE_PATH)
	String cachePath;
	
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.PERSISTENCE_PATH)
	String persistencePath;
	
	@Inject
	@ConfigProperty(name = "quarkus.hibernate-orm.packages")
	String packages;
	
	@Inject
    ManagedExecutor executor;
	
	@RestClient
	Ollama ollama;

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-lang-persistence");
	}
}
