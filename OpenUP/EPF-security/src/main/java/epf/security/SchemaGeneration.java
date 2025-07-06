package epf.security;

import java.util.HashMap;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Persistence;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
@Readiness
public class SchemaGeneration implements HealthCheck {
	
	static final String PERSISTENCE_UNIT = "EPF-Security";
	
	static final String SCHEMA_GENERATION_DATABASE_ACTION = "javax.persistence.schema-generation.database.action";
	
	static final String SCHEMA_GENERATION_CREATE_SOURCE = "javax.persistence.schema-generation.create-source";
	
	static final String SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE = "javax.persistence.schema-generation.create-script-source";
	
	@Inject
	@ConfigProperty(name = SCHEMA_GENERATION_DATABASE_ACTION, defaultValue = "")
	transient String action;
	
	@Inject
	@ConfigProperty(name = SCHEMA_GENERATION_CREATE_SOURCE, defaultValue = "")
	transient String create;
	
	@Inject
	@ConfigProperty(name = SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE, defaultValue = "")
	transient String script;

	@PostConstruct
	void generateSchema() {
		if(!"".equals(action)) {
			final Map<String, Object> properties = new HashMap<>();
			properties.put(SCHEMA_GENERATION_DATABASE_ACTION, action);
			properties.put(SCHEMA_GENERATION_CREATE_SOURCE, create);
			properties.put(SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE, script);
			Persistence.generateSchema(PERSISTENCE_UNIT, properties);
		}
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-security");
	}
}
