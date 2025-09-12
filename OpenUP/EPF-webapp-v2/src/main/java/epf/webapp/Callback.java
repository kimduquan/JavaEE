package epf.webapp;

import epf.naming.Naming;
import epf.webapp.internal.CallbackServlet;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/Callback")
public class Callback extends CallbackServlet {
    
	private static final long serialVersionUID = 1L;
	
	@Inject
	@Named(Naming.CONFIG)
	private Config config;
	
	@Inject
    private OpenIdContext context;

	@Override
	protected OpenIdContext getContext() {
		return context;
	}

	@Override
	protected String getGatewayUrl() {
		return config.getGatewayUrl();
	}
}
