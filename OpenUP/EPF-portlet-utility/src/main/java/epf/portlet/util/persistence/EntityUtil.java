/**
 * 
 */
package epf.portlet.util.persistence;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

import epf.portlet.util.cache.EntityCacheUtil;

/**
 * @author PC
 *
 */
@RequestScoped
public class EntityUtil {

	/**
	 * 
	 */
	@Inject
	private transient EntityCacheUtil persistenceCache;
	
	/**
	 * 
	 */
	@Inject
	private transient PersistenceUtil persistence;
	
	/**
	 * @param schema
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 */
	public List<JsonObject> getEntities(final String schema, final String entity, final Integer firstResult, final Integer maxResults) throws Exception{
		List<JsonObject> objects = persistenceCache.getEntities(schema, entity, firstResult, maxResults);
		if(objects == null || objects.isEmpty()) {
			objects = persistence.getEntities(schema, entity, firstResult, maxResults);
		}
		return objects;
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JsonObject getEntity(final String schema, final String entity, final String id) throws Exception {
		JsonObject object = persistenceCache.getEntity(schema, entity, id);
		if(object == null || object.isEmpty()) {
			object = persistence.getEntity(schema, entity, id);
		}
		return object;
	}
}
