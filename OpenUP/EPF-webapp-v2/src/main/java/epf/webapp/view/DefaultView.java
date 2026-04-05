package epf.webapp.view;

import java.io.Serializable;
import epf.naming.Naming;
import epf.webapp.Session;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ViewScoped
@Named(Naming.WebApp.View.DEFAULT_VIEW)
public class DefaultView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int ORGANIZATION_NAME_MAX_SHOW_LENGTH = 12;
	
	@Inject
	private Session session;
	
	public String getOrganizationUrl() {
		return String.format("http://%s", session.getOrganization().getDomain());
	}
	
	public String getOrganizationName() {
		String name = session.getOrganization().getName();
		if(name.length() > ORGANIZATION_NAME_MAX_SHOW_LENGTH) {
			name = name.substring(0, ORGANIZATION_NAME_MAX_SHOW_LENGTH);
		}
		return name;
	}
	
	public String getPrincipalName() {
		return session.getPrincipal().getGivenName() + " " + session.getPrincipal().getFamilyName();
	}
	
	public String getPrincipalPicture() {
		return session.getPrincipal().getPicture();
	}
}
