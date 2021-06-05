/**
 * 
 */
package epf.rules;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.rules.ConfigurationException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.client.rules.Rules;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Provider {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Provider.class.getName());
	
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
			LOGGER.throwing("Class", "forName", e);
		}
		if(cls != null) {
			try {
				ruleProvider = RuleServiceProviderManager.getRuleServiceProvider(providerUri);
			} 
			catch (ConfigurationException e) {
				LOGGER.throwing("RuleServiceProviderManager", "getRuleServiceProvider", e);
			}
		}
		try {
			ruleRuntime = ruleProvider.getRuleRuntime();
		} 
		catch (ConfigurationException e) {
			LOGGER.throwing("RuleServiceProvider", "getRuleRuntime", e);
		}
	}
	
	public RuleServiceProvider getRuleProvider() {
		return this.ruleProvider;
	}
	
	public RuleRuntime getRuleRuntime() {
		return this.ruleRuntime;
	}
}
