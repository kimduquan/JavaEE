package epf.webapp.security.view;

import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import epf.client.util.Client;
import epf.security.client.Registration;
import epf.webapp.GatewayUtil;
import epf.webapp.naming.Naming;

/**
 * 
 */
@ViewScoped
@Named(Naming.Security.REGISTRATION)
public class RegistrationPage implements Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String token;
	
	/**
	 * 
	 */
	@Inject
    private transient ExternalContext externalContext;
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public String createPrincipal() throws Exception {
		try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
			client.authorization(token.toCharArray());
			try(Response response = Registration.createPrincipal(client)){
				if(response.getStatus() == Status.OK.getStatusCode()) {
					final String redirectUrl = Naming.CONTEXT_ROOT + "/security/login" + Naming.Internal.VIEW_EXTENSION;
					externalContext.redirect(redirectUrl);
				}
			}
		}
		return "";
	}
}
