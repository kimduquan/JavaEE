package epf.portlet.internal.persistence;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import epf.client.query.Query;
import epf.client.util.Client;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.internal.security.SecurityUtil;
import epf.util.json.JsonUtil;

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
		try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.QUERY))){
			try(Response response = Query.executeQuery(
					client, 
					schema,
					path -> path.path(entity), 
					firstResult, 
					maxResults)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					return JsonUtil.readArray(stream)
							.stream()
							.map(value -> value.asJsonObject())
							.collect(Collectors.toList());
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
		try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.QUERY))){
			try(Response response = Query.getEntity(client, schema, entity, id)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					return JsonUtil.readObject(stream);
				}
			}
		}
	}
}
