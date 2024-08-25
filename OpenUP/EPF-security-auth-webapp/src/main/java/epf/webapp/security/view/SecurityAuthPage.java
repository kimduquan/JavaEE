package epf.webapp.security.view;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import epf.util.StringUtil;
import epf.webapp.internal.TokenPrincipal;
import epf.webapp.naming.Naming;
import epf.webapp.security.auth.core.AuthFlowConversation;

/**
 * 
 */
@ViewScoped
@Named(Naming.Security.Auth.AUTH_VIEW)
public class SecurityAuthPage implements Serializable {

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
	private String credential;

	/**
	 * 
	 */
	@Inject
	private transient SecurityContext context;
	
	/**
	 * 
	 */
	@Inject
	private transient Conversation conversation;
	
	/**
	 * 
	 */
	@Inject
	private AuthFlowConversation authFlow;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(final String credential) {
		this.credential = credential;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String authenticate() throws Exception {
		if(conversation.isTransient()) {
			if(url != null && context.getCallerPrincipal() != null) {
				final TokenPrincipal principal = (TokenPrincipal) context.getCallerPrincipal();
				final char[] token = principal.getRememberToken() != null ? principal.getRememberToken() : principal.getRawToken();
				credential = StringUtil.encodeURL(new String(token));
			}
		}
		else {
			if(context.getCallerPrincipal() != null) {
				final TokenPrincipal principal = (TokenPrincipal) context.getCallerPrincipal();
				final char[] token = principal.getRememberToken() != null ? principal.getRememberToken() : principal.getRawToken();
				credential = StringUtil.encodeURL(new String(token));
				url = authFlow.getUrl();
			}
			conversation.end();
		}
		return "";
	}
}
