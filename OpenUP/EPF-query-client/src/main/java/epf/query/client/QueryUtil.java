package epf.query.client;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import epf.naming.Naming;

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
	
	/**
	 * @param attr
	 * @param asc
	 * @return
	 */
	static String sort(final String attr, final boolean asc) {
		return String.format("%s%s%s", attr, Naming.Query.Client.PARAM_SEPARATOR, asc ? Naming.Query.Client.SORT_ASC : Naming.Query.Client.SORT_DESC);
	}
	
	/**
	 * @param attr
	 * @param pattern
	 * @return
	 */
	static String like(final String attr, final String pattern) {
		return String.format("%s%s%s", attr, Naming.Query.Client.PARAM_SEPARATOR, pattern);
	}
}
