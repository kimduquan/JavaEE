package epf.gateway.messaging;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import epf.naming.Naming;
import epf.util.StringUtil;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
public class Remote implements AutoCloseable {
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 *
	 */
	private final URI url;
	
	/**
	 *
	 */
	private final String path;
	
	/**
	 *
	 */
	private transient final Map<String, Session> sessions;
	
	/**
	 * @param uri
	 * @param tenant
	 * @param service
	 * @param path
	 */
	public Remote(final URI uri, final String path) {
		Objects.requireNonNull(uri, "URI");
		this.url = uri;
		this.path = path;
		this.sessions = new ConcurrentHashMap<>();
	}
	
	private void onMessage(final String message, final Session session) {
		if(session != null) {
			sessions.values().forEach(currentSession -> {
				currentSession.getAsyncRemote().sendText(message);
			});
		}
	}

	@Override
	public void close() throws Exception {
		client.close();
	}
	
	/**
	 * @throws Exception
	 */
	public void connectToServer() throws Exception {
		client = Client.connectToServer(url);
		client.onMessage(this::onMessage);
	}
	
	/**
	 * @param session
	 */
	public void putSession(final Session session) {
		sessions.put(session.getId(), session);
	}
	
	/**
	 * @param session
	 * @return
	 */
	public Session removeSession(final Session session) {
		return sessions.remove(session.getId());
	}

	public String getPath() {
		return path;
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @return
	 */
	public static String pathOf(final Optional<String> tenant, final String service, final String... path) {
		return StringUtil.join(tenant.orElse(Naming.Messaging.DEFAULT_PATH), service, path.length > 0 ? StringUtil.join(path) : Naming.Messaging.DEFAULT_PATH);
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @return
	 */
	public static String urlOf(final Optional<String> tenant, final String service, final String... path) {
		return String.join("/", tenant.orElse(Naming.Messaging.DEFAULT_PATH), service, path.length > 0 ? String.join("/", path) : Naming.Messaging.DEFAULT_PATH);
	}
}
