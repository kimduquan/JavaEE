package epf.messaging.client;

import java.net.URI;
import java.util.Optional;
import javax.websocket.ContainerProvider;
import epf.naming.Naming;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public interface Messaging {

	/**
	 * @param uri
	 * @param tenant
	 * @param service
	 * @param token
	 * @param path
	 * @return
	 * @throws Exception
	 */
	static Client connectToServer(final URI uri, final Optional<String> tenant, final String service, final Optional<String> token, final String... path) throws Exception {
		final Client client = new Client();
		final URI endpointUrl = getUrl(uri, tenant, service, token, path);
		client.setSession(ContainerProvider.getWebSocketContainer().connectToServer(client, endpointUrl));
		return client;
	}
	
	/**
	 * @param uri
	 * @param tenant
	 * @param service
	 * @param token
	 * @param path
	 * @return
	 */
	static URI getUrl(final URI uri, final Optional<String> tenant, final String service, final Optional<String> token, final String... path) throws Exception {
		final String tokenParam = token.isPresent() ? "?token=" + StringUtil.encodeURL(token.get()) : "";
		final String urlPath = path.length > 0 ? StringUtil.encodeURL(String.join("/", path)) : Naming.Messaging.DEFAULT_PATH;
		final String endpoint = String.format("%s/%s/%s%s", 
				tenant.orElse(Naming.Messaging.DEFAULT_PATH), 
				service, 
				urlPath, 
				tokenParam);
		return uri.resolve(endpoint);
	}
	
	/**
	 * @param url
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	static Client connectToServer(final URI url, final Class<?> cls) throws Exception {
		final Client client = new Client();
		client.setSession(ContainerProvider.getWebSocketContainer().connectToServer(cls, url));
		return client;
	}
}
