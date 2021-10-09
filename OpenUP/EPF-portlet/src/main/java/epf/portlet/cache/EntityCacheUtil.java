/**
 * 
 */
package epf.portlet.cache;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;

import epf.client.util.Client;
import epf.portlet.gateway.GatewayUtil;
import epf.portlet.security.SecurityUtil;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@RequestScoped
public class EntityCacheUtil {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(EntityCacheUtil.class.getName());

	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityUtil securityUtil;
	
	/**
	 * @param schema
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<JsonObject> getEntities(final String schema, final String entity, final Integer firstResult, final Integer maxResults) {
		List<JsonObject> objects = null;
		try(Client client = securityUtil.newClient(gatewayUtil.get("cache"))){
			try(Response response = epf.client.cache.Cache.getEntities(client, schema, entity, firstResult, maxResults)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						objects = reader
								.readArray()
								.stream()
								.map(value -> value.asJsonObject())
								.collect(Collectors.toList());
					}
				}
			}
		}
		catch(Exception ex) {
			LOGGER.throwing(getClass().getName(), "getEntities", ex);
		}
		return objects;
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @param id
	 * @return
	 */
	public JsonObject getEntity(final String schema, final String entity, final String id) {
		JsonObject object = null;
		try(Client client = securityUtil.newClient(gatewayUtil.get("cache"))){
			try(Response response = epf.client.cache.Cache.getEntity(client, schema, entity, id)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						object = reader.readObject();
					}
				}
			}
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "getEntity", e);
		}
		return object;
	}
}
