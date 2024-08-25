package epf.webapp.security.view;

import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import epf.security.view.LoginView;
import epf.util.StringUtil;
import epf.webapp.internal.Session;
import epf.webapp.naming.Naming;
import epf.webapp.security.AuthParams;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Security.LOGIN)
public class LoginPage implements LoginView, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private String url;
	
	/**
	 * 
	 */
	private String caller;
	
	/**
	 * 
	 */
	private transient char[] password;

	/**
	 * 
	 */
	@Inject
	private transient SecurityContext context;
	
	/**
	 * 
	 */
	@Inject
    private transient ExternalContext externalContext;
	
	/**
	 * 
	 */
	@Inject
	private transient HttpServletRequest request;
	
	/**
	 * 
	 */
	@Inject
	private transient AuthParams authParams;
	
	/**
	 *
	 */
	@Inject
	private transient Session session;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String getCaller() {
		return caller;
	}

	@Override
	public void setCaller(final String caller) {
		this.caller = caller;
	}

	@Override
	public char[] getPassword() {
		return password;
	}

	@Override
	public void setPassword(final char[] password) {
		this.password = password;
	}

	@Override
	public boolean isRememberMe() {
		return authParams.isRememberMe();
	}

	@Override
	public void setRememberMe(final boolean rememberMe) {
		authParams.setRememberMe(rememberMe);
	}

	@Override
	public String login() throws Exception {
		session.clear();
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(caller, new Password(password));
		final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(authParams.isRememberMe());
		final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		final AuthenticationStatus status = context.authenticate(request, response, params);
		if(AuthenticationStatus.SUCCESS.equals(status) || AuthenticationStatus.SEND_CONTINUE.equals(status)) {
			externalContext.redirect("webapp/index.html?url=" + StringUtil.encodeURL(url));
		}
		return "";
	}
}
