package epf.webapp.internal;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import epf.management.schema.Principal;
import epf.naming.Naming;
import jakarta.security.enterprise.authentication.mechanism.http.openid.OpenIdConstant;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class CallbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	final OpenIdContext context = getContext();
        if (context != null) {
        	final ManagementClient management = getManagement();
        	final Principal principal = management.authenticate();
        	final Optional<String> originalRequest = context.getStoredValue(request, response, OpenIdConstant.ORIGINAL_REQUEST);
        	final String originalRequestString = originalRequest.get();
        	final URL originalRequestUrl = URI.create(originalRequestString).toURL();
        	String redirectUrl = originalRequestString;
        	if(originalRequestUrl.getQuery() != null) {
        		if(!originalRequestUrl.getQuery().contains("lang=")) {
        			redirectUrl = redirectUrl + "&lang=" + principal.getLocale();
        		}
        	}
        	else {
        		redirectUrl = redirectUrl + "?lang=" + principal.getLocale();
        	}
        	final boolean isFirstTimeLogin = context.getAccessToken().getClaim(Naming.Management.ORGANIZATION) == null;
        	if(isFirstTimeLogin) {
        		request.logout();
        		return;
        	}
            response.sendRedirect(redirectUrl);
        }
    }
    
    protected abstract OpenIdContext getContext();
    protected abstract String getGatewayUrl();
    
    protected ManagementClient getManagement() {
    	return RestClientBuilder.newBuilder().baseUri(URI.create(getGatewayUrl())).register(AuthFilter.class).build(ManagementClient.class);
    }
}
