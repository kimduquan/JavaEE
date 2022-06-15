package epf.webapp.security.view;

import java.io.Serializable;

import epf.client.mail.Mail;
import epf.client.mail.Message;
import epf.client.util.Client;
import epf.security.client.Security;
import epf.security.view.RegisterView;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import epf.webapp.GatewayUtil;
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
		char[] token = null;
		try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
			try(Response response = Security.createCredential(client, email, password, firstName, lastName)){
				token = response.readEntity(String.class).toCharArray();
			}
		}
		if(token != null) {
			final Message message = new Message();
			message.setFrom(email);
			message.setSubject("");
			message.setText(new String(token));
			try(Client client = gatewayUtil.newClient(epf.naming.Naming.MAIL)){
				client.authorization(token);
				try(Response res = Mail.send(client, message)){
					
				}
			}
		}
		return "";
	}
}
