/**
 * 
 */
package epf.net;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import epf.client.gateway.GatewayUtil;
import epf.client.persistence.Entities;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.net.schema.URL;
import epf.util.StringUtil;
import epf.util.http.SessionUtil;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.NET)
@ApplicationScoped
public class Net implements epf.client.net.Net {
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;

	@Override
	public String rewriteUrl(final java.net.URL rawUrl, final HttpServletRequest request, final SecurityContext security) throws Exception {
		final URL url = new URL();
		url.setAuthority(rawUrl.getAuthority());
		url.setDefaultPort(rawUrl.getDefaultPort());
		url.setFile(rawUrl.getFile());
		url.setHost(rawUrl.getHost());
		url.setPath(rawUrl.getPath());
		url.setPort(rawUrl.getPort());
		url.setProtocol(rawUrl.getProtocol());
		url.setQuery(rawUrl.getQuery());
		url.setRef(rawUrl.getRef());
		url.setString(rawUrl.toString());
		url.setUserInfo(rawUrl.getUserInfo());
		try(Client client = clientUtil.newClient(GatewayUtil.get(Naming.PERSISTENCE))){
			client.authorizationHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
			final URL resultUrl = Entities.persist(client, URL.class, epf.net.schema.Net.SCHEMA, epf.net.schema.Net.URL, url);
			final String shortString = StringUtil.toShortString(resultUrl.getId());
			SessionUtil.setMapAttribute(request, Naming.Net.NET_URL, "urls", shortString, rawUrl.toString());
			return shortString;
		}
	}
}
