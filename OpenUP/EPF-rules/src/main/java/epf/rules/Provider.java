package epf.rules;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.rules.ConfigurationException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.client.rules.Rules;
import epf.util.logging.LogManager;

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
	private static final Logger LOGGER = LogManager.getLogger(Provider.class.getName());
	
	/**
	 * 
	 */
	private transient RuleServiceProvider ruleProvider;
	
	/**
	 * 
	 */
	private transient RuleRuntime ruleRuntime;

	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Rules.PROVIDER_CLASS)
	private transient String providerClass;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Rules.PROVIDER_URI)
	private transient String providerUri;
	
	/**
	 * 
	 */
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
