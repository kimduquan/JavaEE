/**
 * 
 */
package epf.portlet.persistence;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import epf.portlet.cache.EntityCacheUtil;

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
	 * @param name
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<JsonObject> getEntities(final String name, final Integer firstResult, final Integer maxResults) throws Exception{
		List<JsonObject> objects = persistenceCache.getEntities(name, firstResult, maxResults);
		if(objects == null || objects.isEmpty()) {
			objects = persistence.getEntities(name, firstResult, maxResults);
		}
		return objects;
	}
	
	/**
	 * @param name
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JsonObject getEntity(final String name, final String id) throws Exception {
		JsonObject object = persistenceCache.getEntity(name, id);
		if(object == null || object.isEmpty()) {
			object = persistence.getEntity(name, id);
		}
		return object;
	}
}
