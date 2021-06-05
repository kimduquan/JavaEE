/**
 * 
 */
package epf.rules;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.rules.ConfigurationException;
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
		try {
			Class.forName(providerClass);
			ruleProvider = RuleServiceProviderManager.getRuleServiceProvider(providerUri);
		} 
		catch (ClassNotFoundException | ConfigurationException e) {
			LOGGER.throwing("Class", "forName", e);
		}
	}
	
	public RuleServiceProvider getRuleProvider() {
		return this.ruleProvider;
	}
}
