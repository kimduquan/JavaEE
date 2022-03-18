package epf.security;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Persistence;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.security.util.IdentityStore;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class SchemaGeneration implements HealthCheck {
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = IdentityStore.SCHEMA_GENERATION_DATABASE_ACTION, defaultValue = "")
	transient String action;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = IdentityStore.SCHEMA_GENERATION_CREATE_SOURCE, defaultValue = "")
	transient String create;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = IdentityStore.SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE, defaultValue = "")
	transient String script;

	/**
	 * 
	 */
	@PostConstruct
	void generateSchema() {
		if(!"".equals(action)) {
			final Map<String, Object> properties = new HashMap<>();
			properties.put(IdentityStore.SCHEMA_GENERATION_DATABASE_ACTION, action);
			properties.put(IdentityStore.SCHEMA_GENERATION_CREATE_SOURCE, create);
			properties.put(IdentityStore.SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE, script);
			Persistence.generateSchema(IdentityStore.PERSISTENCE_UNIT, properties);
		}
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-security");
	}
}
