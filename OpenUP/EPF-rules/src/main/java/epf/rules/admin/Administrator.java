/**
 * 
 */
package epf.rules.admin;

import java.rmi.RemoteException;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.rules.ConfigurationException;
import javax.rules.RuleServiceProvider;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import epf.rules.Provider;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Administrator {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Admin.class.getName());
	
	/**
	 * 
	 */
	private transient RuleAdministrator ruleAdmin;
	
	/**
	 * 
	 */
	private transient LocalRuleExecutionSetProvider localRuleProvider;

	/**
	 * 
	 */
	@Inject
	private transient Provider provider;
	
	@PostConstruct
	protected void postConstruct() {
		try {
			ruleAdmin = provider.getRuleProvider().getRuleAdministrator();
		} 
		catch (ConfigurationException e) {
			LOGGER.throwing(RuleServiceProvider.class.getName(), "getRuleAdministrator", e);
		}
		if(ruleAdmin != null) {
			try {
				localRuleProvider = ruleAdmin.getLocalRuleExecutionSetProvider(null);
			} 
			catch (RemoteException e) {
				LOGGER.throwing(RuleAdministrator.class.getName(), "getLocalRuleExecutionSetProvider", e);
			}
		}
	}
	
	public RuleAdministrator getRuleAdmin() {
		return ruleAdmin;
	}
	
	public LocalRuleExecutionSetProvider getLocalRuleProvider() {
		return this.localRuleProvider;
	}
}
