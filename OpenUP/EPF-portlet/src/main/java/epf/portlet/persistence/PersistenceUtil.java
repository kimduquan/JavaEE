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
import epf.portlet.gateway.GatewayUtil;
import epf.portlet.security.SecurityUtil;
import epf.util.client.Client;

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
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<JsonObject> getEntities(final String name, final Integer firstResult, final Integer maxResults) throws Exception{
		try(Client client = securityUtil.newClient(gatewayUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Queries.executeQuery(
					client, 
					path -> path.path(name), 
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
	 * @param name
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JsonObject getEntity(final String name, final String id) throws Exception {
		try(Client client = securityUtil.newClient(gatewayUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Entities.find(client, name, id)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						return reader.readObject();
					}
				}
			}
		}
	}
}
