/**
 * 
 */
package epf.rules;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;
import javax.rules.StatefulRuleSession;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@SessionScoped
public class Session implements Serializable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Session.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient StatefulRuleSession ruleSession;
	
	/**
	 * 
	 */
	@Inject
	private transient Provider provider;
	
	@PostConstruct
	protected void postConstruct() {
		try {
			ruleSession = (StatefulRuleSession) provider
					.getRuleRuntime()
					.createRuleSession(
							"",
							null, 
							RuleRuntime.STATEFUL_SESSION_TYPE
							);
		} 
		catch (
				RuleSessionTypeUnsupportedException 
				| RuleSessionCreateException
				| RuleExecutionSetNotFoundException 
				| RemoteException e) {
			LOGGER.throwing(RuleRuntime.class.getName(), "createRuleSession", e);
		}
	}

	public StatefulRuleSession getRuleSession() {
		return ruleSession;
	}
}
