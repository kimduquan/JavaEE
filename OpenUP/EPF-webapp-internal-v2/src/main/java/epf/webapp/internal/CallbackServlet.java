package epf.webapp.internal;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import epf.management.schema.Session;
import epf.naming.Naming;
import jakarta.security.enterprise.authentication.mechanism.http.openid.OpenIdConstant;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class CallbackServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    
    private String buildLogoutUrl(final OpenIdContext context, final Optional<String> originalRequest) {
    	final String endSessionEndpoint = context.getProviderMetadata().getString(OpenIdConstant.END_SESSION_ENDPOINT);
		final String idTokenHint = context.getIdentityToken().getToken();
		String postLogoutRedirectUri = null;
		if(originalRequest.isPresent()) {
			postLogoutRedirectUri = originalRequest.get();
		}
		final StringBuilder logoutUrl = new StringBuilder();
		logoutUrl.append(endSessionEndpoint).append('?').append(OpenIdConstant.ID_TOKEN_HINT).append('=').append(idTokenHint);
		if(postLogoutRedirectUri != null) {
			logoutUrl.append('&').append(OpenIdConstant.POST_LOGOUT_REDIRECT_URI).append('=').append(postLogoutRedirectUri);
		}
		return logoutUrl.toString();
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	final OpenIdContext context = getContext();
        if (context != null) {
        	final ManagementClient management = getManagement();
        	final Session session = management.newSession();
        	final boolean isFirstTimeLogin = context.getAccessToken().getClaim(Naming.Management.ORGANIZATION) == null;
        	final Optional<String> originalRequest = context.getStoredValue(request, response, OpenIdConstant.ORIGINAL_REQUEST);
        	if(isFirstTimeLogin) {
        		final String logoutUrl = buildLogoutUrl(context, originalRequest);
        		response.sendRedirect(logoutUrl);
        	}
        	else {
        		session.getPrincipal().setPicture(context.getClaims().getPicture().orElse(null));
        		session.getPrincipal().setProfile(context.getClaims().getProfile().orElse(null));
        		getSession().setOrganization(session.getOrganization());
        		getSession().setPrincipal(session.getPrincipal());
            	final String originalRequestString = originalRequest.get();
            	final URL originalRequestUrl = URI.create(originalRequestString).toURL();
            	String redirectUrl = originalRequestString;
            	final String locale = session.getPrincipal().getLocale();
            	if(originalRequestUrl.getQuery() != null) {
            		if(!originalRequestUrl.getQuery().contains("lang=")) {
            			redirectUrl = redirectUrl + "&lang=" + locale;
            		}
            	}
            	else {
            		redirectUrl = redirectUrl + "?lang=" + locale;
            	}
                response.sendRedirect(redirectUrl);
        	}
        }
    }
    
    protected abstract OpenIdContext getContext();
    protected abstract String getGatewayUrl();
    protected abstract Session getSession();
    
    protected ManagementClient getManagement() {
    	return RestClientBuilder.newBuilder().baseUri(URI.create(getGatewayUrl())).register(AuthFilter.class).build(ManagementClient.class);
    }
}
