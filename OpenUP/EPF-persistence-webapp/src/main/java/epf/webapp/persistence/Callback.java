package epf.webapp.persistence;

import epf.webapp.internal.CallbackServlet;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/Callback")
public class Callback extends CallbackServlet {
    
	private static final long serialVersionUID = 1L;
	
	@Inject
    private OpenIdContext context;

	@Override
	protected OpenIdContext getContext() {
		return context;
	}
}
