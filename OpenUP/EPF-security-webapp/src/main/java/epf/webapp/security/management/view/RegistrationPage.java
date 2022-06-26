package epf.webapp.security.management.view;

import java.io.Serializable;
import java.security.PrivateKey;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import epf.client.util.Client;
import epf.security.client.Management;
import epf.security.management.view.RegistrationView;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.SecurityUtil;
import epf.webapp.naming.Naming;
import epf.webapp.util.WebAppException;

/**
 * 
 */
@ViewScoped
@Named(Naming.Security.REGISTRATION)
public class RegistrationPage implements RegistrationView, Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String code;
	
	/**
	 *
	 */
	private String data;
	
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
	
	/**
	 *
	 */
	@Inject
	private transient SecurityUtil securityUtil;

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(final String data) {
		this.data = data;
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public String createPrincipal() throws Exception {
		final PrivateKey privateKey = securityUtil.getPrivateKey();
		try {
			final String token = epf.util.security.SecurityUtil.decrypt(code, data, privateKey);
			try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
				client.authorization(token.toCharArray());
				try(Response response = Management.createPrincipal(client)){
					if(response.getStatus() == Status.OK.getStatusCode()) {
						final String redirectUrl = Naming.CONTEXT_ROOT + Naming.View.LOGIN_PAGE;
						externalContext.redirect(redirectUrl);
					}
					else {
						externalContext.responseSendError(response.getStatus(), response.getStatusInfo().getReasonPhrase());
					}
				}
			}
		}
		catch(Exception ex) {
			throw new WebAppException(Status.BAD_REQUEST.getStatusCode(), ex);
		}
		return "";
	}
}
