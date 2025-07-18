package epf.rules;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.rules.ConfigurationException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.util.logging.LogManager;

@ApplicationScoped
@Readiness
public class Provider implements HealthCheck {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Provider.class.getName());
	
	private transient RuleServiceProvider ruleProvider;
	
	private transient RuleRuntime ruleRuntime;

	@Inject
	@ConfigProperty(name = Naming.Rules.PROVIDER_CLASS)
	private transient String providerClass;
	
	@Inject
	@ConfigProperty(name = Naming.Rules.PROVIDER_URI)
	private transient String providerUri;
	
	@PostConstruct
	protected void postConstruct() {
		Class<?> cls = null;
		try {
			cls = Class.forName(providerClass);
		} 
		catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Class.name", e);
		}
		if(cls != null) {
			try {
				ruleProvider = RuleServiceProviderManager.getRuleServiceProvider(providerUri);
			} 
			catch (ConfigurationException e) {
				LOGGER.log(Level.SEVERE, "RuleServiceProviderManager.ruleServiceProvider", e);
			}
		}
		try {
			ruleRuntime = ruleProvider.getRuleRuntime();
		} 
		catch (ConfigurationException e) {
			LOGGER.log(Level.SEVERE, "RuleServiceProvider.ruleRuntime", e);
		}
	}
	
	public RuleServiceProvider getRuleProvider() {
		return this.ruleProvider;
	}
	
	public RuleRuntime getRuleRuntime() {
		return this.ruleRuntime;
	}

	@Override
	public HealthCheckResponse call() {
		HealthCheckResponse response = HealthCheckResponse.up("EPF-rules");
		if(ruleRuntime == null) {
			response = HealthCheckResponse.down("EPF-rules-runtime");
		}
		if(ruleProvider == null) {
			response = HealthCheckResponse.down("EPF-rules-provider");
		}
		return response;
	}
}
