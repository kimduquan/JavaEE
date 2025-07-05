package epf.webapp.security.management.view;

import java.io.Serializable;
import java.security.PrivateKey;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import epf.client.util.Client;
import epf.security.client.Management;
import epf.security.client.Security;
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
			boolean isOk = false;
			try(Client client = gatewayUtil.newClient(epf.naming.Naming.Security.SECURITY_MANAGEMENT)){
				client.authorization(token.toCharArray());
				try(Response response = Management.activeCredential(client)){
					if(response.getStatus() == Status.OK.getStatusCode()) {
						isOk = true;
					}
					else {
						externalContext.responseSendError(response.getStatus(), response.getStatusInfo().getReasonPhrase());
					}
				}
			}
			if(isOk) {
				try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
					client.authorization(token.toCharArray());
					try(Response response = Security.createPrincipal(client)){
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
		}
		catch(Exception ex) {
			throw new WebAppException(Status.BAD_REQUEST.getStatusCode(), ex);
		}
		return "";
	}
}
