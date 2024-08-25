package epf.query.util;

import java.util.List;
import java.util.Map.Entry;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Link.Builder;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import epf.query.client.EntityId;

/**
 * 
 */
public interface LinkUtil {

	/**
	 * @param path
	 * @param rel
	 * @param entityId
	 * @return
	 */
	static Link build(final String path, final String rel, final EntityId entityId) {
		Builder builder = Link.fromPath(path).rel(rel).type(entityId.getSchema()).title(entityId.getName());
		for(Entry<String, Object> entry : entityId.getAttributes().entrySet()) {
			builder = builder.param(entry.getKey(), String.valueOf(entry.getValue()));
		}
		return builder.build();
	}
	
	/**
	 * @param builder
	 * @param path
	 * @param entityIds
	 * @return
	 */
	static ResponseBuilder links(ResponseBuilder builder, final String path, final List<EntityId> entityIds) {
		int index = 0;
		for(EntityId entityId : entityIds) {
			builder = builder.links(build(path, "#" + index, entityId));
			index++;
		}
		return builder;
	}
}
