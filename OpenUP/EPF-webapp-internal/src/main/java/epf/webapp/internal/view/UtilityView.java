package epf.webapp.internal.view;

import java.io.Serializable;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import epf.webapp.naming.Naming;

@ViewScoped
@Named(Naming.Internal.UTILITY_VIEW)
public class UtilityView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
    private transient ExternalContext externalContext;
	
	public String redirect() throws Exception {
		final String url = externalContext.getRequestParameterMap().get("url");
		if(url != null && !url.isEmpty()) {
			externalContext.redirect(url);
		}
		return "";
	}
}
