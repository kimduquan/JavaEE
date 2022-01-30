package epf.security.webapp.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@FacesConfig
@CustomFormAuthenticationMechanismDefinition(
		loginToContinue = @LoginToContinue(
				loginPage = epf.security.webapp.Naming.LOGIN_PAGE,
				useForwardToLogin = false
				)
		)
@DeclareRoles(value = { Naming.Security.DEFAULT_ROLE })
public class Config {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Config.class.getName());

	/**
	 * 
	 */
	private final Map<String, String> properties = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = clientUtil.newClient(ConfigUtil.getURI(Naming.Gateway.GATEWAY_URL))){
			final Map<String, String> props = epf.client.config.Config.getProperties(client, "");
			props.forEach(properties::put);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String getProperty(final String name) {
		return properties.get(name);
	}
}
