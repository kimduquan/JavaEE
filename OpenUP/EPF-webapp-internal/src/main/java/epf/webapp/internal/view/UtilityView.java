package epf.webapp.internal.view;

import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Internal.UTILITY_VIEW)
public class UtilityView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Inject
    private transient ExternalContext externalContext;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String redirect() throws Exception {
		final String url = externalContext.getRequestParameterMap().get("url");
		if(url != null && !url.isEmpty()) {
			externalContext.redirect(url);
		}
		return "";
	}
}
