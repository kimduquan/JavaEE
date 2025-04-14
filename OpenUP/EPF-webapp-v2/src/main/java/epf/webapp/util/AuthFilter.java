package epf.webapp.util;

import java.io.IOException;
import java.util.Arrays;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.openid.AccessToken;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;

@RequestScoped
public class AuthFilter implements ClientRequestFilter {
	
	@Inject
	private OpenIdContext context;

	@Override
	public void filter(final ClientRequestContext requestContext) throws IOException {
		final String type = context.getAccessToken().getType().equals(AccessToken.Type.BEARER) ? "Bearer" : "";
		requestContext.getHeaders().put(HttpHeaders.AUTHORIZATION, Arrays.asList(type + " " + context.getAccessToken().getToken()));
	}
}
