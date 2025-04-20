package epf.query.client;

import java.net.URI;
import jakarta.ws.rs.core.UriBuilder;
import epf.naming.Naming;

public interface QueryUtil {

	static UriBuilder fromEntityId(final URI baseUrl, final EntityId entityId) {
		return UriBuilder.fromUri(baseUrl)
				.path("entity")
				.path(entityId.getSchema())
				.path(entityId.getName())
				.path(String.valueOf(entityId.getAttributes().values().iterator().next()));
	}
	
	static String sort(final String attr, final boolean asc) {
		return String.format("%s%s%s", attr, Naming.Query.Client.PARAM_SEPARATOR, asc ? Naming.Query.Client.SORT_ASC : Naming.Query.Client.SORT_DESC);
	}
	
	static String like(final String attr, final String pattern) {
		return String.format("%s%s%s", attr, Naming.Query.Client.PARAM_SEPARATOR, pattern);
	}
}
