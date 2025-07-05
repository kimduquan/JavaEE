package epf.rules.admin;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.rules.ConfigurationException;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.rules.Provider;
import epf.util.logging.LogManager;

@ApplicationScoped
@Readiness
public class Administrator implements HealthCheck {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Administrator.class.getName());
	
	private transient RuleAdministrator ruleAdmin;
	
	private transient LocalRuleExecutionSetProvider localRuleProvider;

	@Inject @Readiness
	private transient Provider provider;
	
	@PostConstruct
	protected void postConstruct() {
		try {
			ruleAdmin = provider.getRuleProvider().getRuleAdministrator();
		} 
		catch (ConfigurationException e) {
			LOGGER.log(Level.SEVERE, "Provider.ruleProvider.ruleAdministrator", e);
		}
		if(ruleAdmin != null) {
			try {
				localRuleProvider = ruleAdmin.getLocalRuleExecutionSetProvider(null);
			} 
			catch (RemoteException e) {
				LOGGER.log(Level.SEVERE, "RuleAdministrator.localRuleExecutionSetProvider", e);
			}
		}
	}
	
	public RuleAdministrator getRuleAdmin() {
		return ruleAdmin;
	}
	
	public LocalRuleExecutionSetProvider getLocalRuleProvider() {
		return this.localRuleProvider;
	}

	@Override
	public HealthCheckResponse call() {
		HealthCheckResponse response = HealthCheckResponse.up("EPF-rules-admin");
		if(localRuleProvider == null || ruleAdmin == null) {
			response = HealthCheckResponse.down("EPF-rules-admin");
		}
		return response;
	}
}
