package epf.rules;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionSetNotFoundException;
import javax.rules.RuleRuntime;
import javax.rules.RuleSessionCreateException;
import javax.rules.RuleSessionTypeUnsupportedException;
import javax.rules.StatefulRuleSession;
import org.eclipse.microprofile.health.Readiness;
import epf.util.logging.LogManager;

@SessionScoped
public class Session implements Serializable {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Session.class.getName());

	private static final long serialVersionUID = 1L;
	
	private transient final Map<String, StatefulRuleSession> statefulSessions = new ConcurrentHashMap<>();
	
	@Inject @Readiness
	private transient Provider provider;
	
	@PreDestroy
	protected void preDestroy() {
		statefulSessions.forEach((name, session) -> {
			try {
				session.release();
			} 
			catch (InvalidRuleSessionException | RemoteException e) {
				LOGGER.log(Level.SEVERE, "statefulSessions", e);
			}
		});
	}
	
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
			LOGGER.log(Level.SEVERE, "createRuleSession", e);
		}
		return ruleSession;
	}

	public StatefulRuleSession getRuleSession(final String ruleSet) {
		return statefulSessions.computeIfAbsent(ruleSet, this::createRuleSession);
	}
}
