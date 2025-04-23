package epf.webapp.security.view;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.logging.Logger;
import epf.client.util.Client;
import epf.mail.client.Mail;
import epf.mail.client.Message;
import epf.security.client.Management;
import epf.security.view.RegisterView;
import epf.util.StringUtil;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.SecurityUtil;
import epf.webapp.naming.Naming;

/**
 * 
 */
@ViewScoped
@Named(Naming.Security.REGISTER)
public class RegisterPage implements RegisterView, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(RegisterPage.class.getName());
	
	/**
	 *
	 */
	private String email;
	
	/**
	 *
	 */
	private String firstName;
	
	/**
	 *
	 */
	private String lastName;
	
	/**
	 *
	 */
	private String password;
	
	/**
	 *
	 */
	private String repeatPassword;
	
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
	
	/**
	 * 
	 */
	@Inject
    private transient ExternalContext externalContext;

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public String getRepeatPassword() {
		return repeatPassword;
	}

	@Override
	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	@Override
	public String register() throws Exception {
		String token = null;
		try(Client client = gatewayUtil.newClient(epf.naming.Naming.Security.SECURITY_MANAGEMENT)){
			try(Response response = Management.createCredential(client, email, password, firstName, lastName)){
				if(response.getStatus() == Status.OK.getStatusCode()) {
					token = response.readEntity(String.class);
				}
				else {
					externalContext.responseSendError(response.getStatus(), response.getStatusInfo().getReasonPhrase());
				}
			}
		}
		if(token != null && !token.isEmpty()) {
			final PublicKey publicKey = securityUtil.getPublicKey();
			final StringBuilder data = new StringBuilder();
			final String code = epf.util.security.SecurityUtil.encrypt(token, data, publicKey);
			final String securityWebAppUrl = ConfigUtil.getString(epf.naming.Naming.WebApp.SECURITY_WEB_APP_URL);
			final String registrationUrl = securityWebAppUrl + Naming.Security.View.REGISTRATION_PAGE + "?code=" + StringUtil.encodeURL(code) + "&data=" + StringUtil.encodeURL(data.toString());
			final Message message = new Message();
			message.setRecipients(new HashMap<>());
			message.getRecipients().put(Message.TO, email);
			message.setSubject(email);
			message.setText(registrationUrl);
			try(Client client = gatewayUtil.newClient(epf.naming.Naming.MAIL)){
				client.authorization(token.toCharArray());
				try(Response res = Mail.send(client, message)){
					if(res.getStatus() == Status.OK.getStatusCode()) {
						final String redirectUrl = Naming.CONTEXT_ROOT + Naming.View.LOGIN_PAGE;
						externalContext.redirect(redirectUrl);
					}
					else {
						LOGGER.severe(registrationUrl);
						externalContext.responseSendError(res.getStatus(), res.getStatusInfo().getReasonPhrase());
					}
				}
			}
		}
		return "";
	}
}
