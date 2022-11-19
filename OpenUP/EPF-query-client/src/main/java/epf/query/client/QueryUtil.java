package epf.query.client;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;

/**
 * 
 */
public interface QueryUtil {

	/**
	 * @param baseUrl
	 * @param entityId
	 * @return
	 */
	static UriBuilder fromEntityId(final URI baseUrl, final EntityId entityId) {
		return UriBuilder.fromUri(baseUrl)
				.path("entity")
				.path(entityId.getSchema())
				.path(entityId.getName())
				.path(String.valueOf(entityId.getAttributes().values().iterator().next()));
	}
}
