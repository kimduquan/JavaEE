/**
 * 
 */
package epf.rules;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.rules.InvalidRuleSessionException;
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
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		if(ruleSession != null) {
			try {
				ruleSession.release();
			} 
			catch (InvalidRuleSessionException | RemoteException e) {
				LOGGER.throwing(StatefulRuleSession.class.getName(), "release", e);
			}
		}
	}
	
	/**
	 * @param ruleSet
	 * @return
	 */
	protected StatefulRuleSession createRuleSession(final String ruleSet) {
		StatefulRuleSession ruleSession = null;
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
		return ruleSession;
	}

	/**
	 * @param ruleSet
	 * @return
	 */
	public StatefulRuleSession getRuleSession(final String ruleSet) {
		if(ruleSession == null) {
			ruleSession = this.createRuleSession(ruleSet);
		}
		return ruleSession;
	}
}
