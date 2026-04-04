package epf.webapp.view;

import java.io.Serializable;
import epf.management.schema.Organization;
import epf.management.schema.Principal;
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
	
	@Inject
	private Session session;
	
	public Principal getPrincipal() {
		return session.getPrincipal();
	}
	
	public Organization getOrganization() {
		return session.getOrganization();
	}
	
	public String getOrganizationUrl() {
		return String.format("http://%s", session.getOrganization().getDomain());
	}
}
