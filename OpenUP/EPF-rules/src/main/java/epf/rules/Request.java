package epf.rules;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;
import javax.rules.StatelessRuleSession;
import org.eclipse.microprofile.health.Readiness;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@RequestScoped
public class Request {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Request.class.getName());
	
	/**
	 * 
	 */
	private transient StatelessRuleSession ruleSession;

	/**
	 * 
	 */
	@Inject @Readiness
	private transient Provider provider;
	
	/**
	 * 
	 */
	@PreDestroy
	protected void release() {
		if(ruleSession != null) {
			try {
				ruleSession.release();
			} 
			catch (
					InvalidRuleSessionException 
					| RemoteException e) {
				LOGGER.log(Level.SEVERE, "release", e);
			}
		}
	}
	
	/**
	 * @param ruleSet
	 * @return
	 */
	public StatelessRuleSession createRuleSession(final String ruleSet) {
		try {
			ruleSession = (StatelessRuleSession) provider
					.getRuleRuntime()
					.createRuleSession(
							ruleSet, 
							new HashMap<Object, Object>(), 
							RuleRuntime.STATELESS_SESSION_TYPE
							);
		} 
		catch (
				RuleSessionTypeUnsupportedException 
				| RuleSessionCreateException 
				| RuleExecutionSetNotFoundException
				| RemoteException e) {
			LOGGER.log(Level.SEVERE, "createRuleSession", e);
		}
		return this.ruleSession;
	}
}
