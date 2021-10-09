/**
 * 
 */
package epf.portlet.persistence;

import java.io.InputStream;
import java.util.List;
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

/**
 * @author PC
 *
 */
@RequestScoped
public class PersistenceUtil {

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
	 */
	public List<JsonObject> getEntities(final String schema, final String entity, final Integer firstResult, final Integer maxResults) throws Exception{
		try(Client client = securityUtil.newClient(gatewayUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Queries.executeQuery(
					client, 
					schema,
					path -> path.path(entity), 
					firstResult, 
					maxResults)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						return reader
						.readArray()
						.stream()
						.map(value -> value.asJsonObject())
						.collect(Collectors.toList());
					}
				}
			}
		}
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JsonObject getEntity(final String schema, final String entity, final String id) throws Exception {
		try(Client client = securityUtil.newClient(gatewayUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Entities.find(client, schema, entity, id)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						return reader.readObject();
					}
				}
			}
		}
	}
}
