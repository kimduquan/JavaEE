/**
 * 
 */
package epf.rules.admin;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.rules.ConfigurationException;
import javax.rules.RuleServiceProvider;
import javax.rules.admin.RuleAdministrator;
import javax.ws.rs.Path;
import epf.rules.Provider;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@Path("rules/admin")
public class Admin implements epf.client.rules.admin.Admin {
	
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
	}
	
	public RuleAdministrator getRuleAdmin() {
		return ruleAdmin;
	}
}
