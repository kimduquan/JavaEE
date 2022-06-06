package epf.portlet.internal.cache;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.internal.security.SecurityUtil;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@RequestScoped
public class EntityCacheUtil {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(EntityCacheUtil.class.getName());

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
		try(Client client = securityUtil.newClient(gatewayUtil.get(Naming.QUERY))){
			try(Response response = epf.client.query.Query.executeQuery(client, schema, t -> t.path(entity), firstResult, maxResults)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					objects = JsonUtil.readArray(stream)
							.stream()
							.map(value -> value.asJsonObject())
							.collect(Collectors.toList());
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
		try(Client client = securityUtil.newClient(gatewayUtil.get(Naming.QUERY))){
			try(Response response = epf.client.query.Query.getEntity(client, schema, entity, id)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					object = JsonUtil.readObject(stream);
				}
			}
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "getEntity", e);
		}
		return object;
	}
}
