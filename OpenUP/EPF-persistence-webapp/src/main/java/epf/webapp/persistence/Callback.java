package epf.webapp.persistence;

import epf.naming.Naming;
import epf.webapp.internal.CallbackServlet;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(Naming.WebApp.Internal.CALLBACK)
public class Callback extends CallbackServlet {
    
	private static final long serialVersionUID = 1L;
	
	@Inject
	@Named(Naming.CONFIG)
	private Config config;
	
	@Inject
    private OpenIdContext context;
	
	@Inject
	private Session session;

	@Override
	protected OpenIdContext getContext() {
		return context;
	}

	@Override
	protected String getGatewayUrl() {
		return config.getGatewayUrl();
	}

	@Override
	protected epf.management.schema.Session getSession() {
		return session;
	}
}
