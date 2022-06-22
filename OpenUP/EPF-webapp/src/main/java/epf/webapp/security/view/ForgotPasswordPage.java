package epf.webapp.security.view;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import epf.client.mail.Mail;
import epf.client.mail.Message;
import epf.client.util.Client;
import epf.security.client.Management;
import epf.security.view.ForgotPasswordView;
import epf.util.StringUtil;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;
import epf.webapp.SecurityUtil;
import epf.webapp.naming.Naming;

/**
 * 
 */
@ViewScoped
@Named(Naming.Security.FORGOT_PASSWORD)
public class ForgotPasswordPage implements ForgotPasswordView, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(ForgotPasswordPage.class.getName());
	
	/**
	 *
	 */
	private String email;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	
	/* (non-Javadoc)
	 * @see epf.security.view.ResetPasswordView#reset()
	 */
	public String reset() throws Exception {
		String token = null;
		try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
			try(Response response = Management.resetPassword(client, email)){
				token = response.readEntity(String.class);
			}
		}
		if(token != null && !token.isEmpty()) {
			final PublicKey publicKey = securityUtil.getTrustStore().getCertificate(securityUtil.getKeyAlias()).getPublicKey();
			final StringBuilder data = new StringBuilder();
			final String code = epf.util.security.SecurityUtil.encrypt(token, data, publicKey);
			final String webAppUrl = ConfigUtil.getString(epf.naming.Naming.WebApp.WEB_APP_URL);
			final String resetPasswordUrl = webAppUrl + Naming.View.RESET_PASSWORD_PAGE + "?code=" + StringUtil.encodeURL(code) + "&data=" + StringUtil.encodeURL(data.toString());
			final Message message = new Message();
			message.setRecipients(new HashMap<>());
			message.getRecipients().put(Message.TO, email);
			message.setSubject(email);
			message.setText(resetPasswordUrl);
			try(Client client = gatewayUtil.newClient(epf.naming.Naming.MAIL)){
				client.authorization(token.toCharArray());
				try(Response res = Mail.send(client, message)){
					if(res.getStatus() == Status.OK.getStatusCode()) {
						final String redirectUrl = Naming.CONTEXT_ROOT + Naming.View.LOGIN_PAGE;
						externalContext.redirect(redirectUrl);
					}
					else {
						LOGGER.severe(resetPasswordUrl);
					}
				}
			}
		}
		return "";
	}
}
