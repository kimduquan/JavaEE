package epf.webapp.query;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.persistence.schema.client.Entity;
import epf.query.client.Query;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
import epf.webapp.internal.GatewayUtil;

/**
 * 
 */
public class QueryCollector extends LazyDataModel<JsonObject> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(QueryCollector.class.getName());
	
	/**
	 *
	 */
	private transient final GatewayUtil gateway;
	
	/**
	 *
	 */
	private transient final char[] token;
	
	/**
	 *
	 */
	private final String schema;
	
	/**
	 *
	 */
	private final Entity entity;
	
	/**
	 *
	 */
	private final Map<String, JsonObject> entityMap = new HashMap<>();
	
	/**
	 * @param gateway
	 * @param token
	 * @param schema
	 * @param entity
	 */
	public QueryCollector(final GatewayUtil gateway, final char[] token, final String schema, final Entity entity) {
		this.gateway = gateway;
		this.token = token;
		this.schema = schema;
		this.entity = entity;
	}
	
	/**
	 * @param target
	 * @param entity
	 * @param filters
	 * @return
	 */
	private WebTarget buildFilters(final WebTarget target, final String entity, final Collection<FilterMeta> filters) {
		WebTarget newTarget = target.path(entity);
		for(FilterMeta filter : filters) {
			newTarget = newTarget.matrixParam(filter.getField(), filter.getFilterValue());
		}
		return newTarget;
	}

	@Override
	public List<JsonObject> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        final String[] sort = sortBy.keySet().toArray(new String[0]);
        final Integer firstResult = first;
        final Integer maxResults = pageSize;
		try(Client client = gateway.newClient(Naming.QUERY)){
			client.authorization(token);
        	try(Response response = Query.executeQuery(client, schema, target -> buildFilters(target, entity.getName(), filterBy.values()), firstResult, maxResults, sort)){
        		try(InputStream stream = response.readEntity(InputStream.class)){
        			entityMap.clear();
        			return JsonUtil.readArray(stream)
							.stream()
							.map(value -> value.asJsonObject())
							.collect(Collectors.toList());
				}
        	}
        } 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[QueryCollector.load]", e);
		}
		return Arrays.asList();
    }

    @Override
    public JsonObject getRowData(final String rowKey) {
        return entityMap.get(rowKey);
    }

    @Override
    public String getRowKey(final JsonObject object) {
    	final String rowKey = object.get(entity.getId().getName()).toString();
    	entityMap.put(rowKey, object);
        return rowKey;
    }

	@Override
	public int count(final Map<String, FilterMeta> filterBy) {
		try(Client client = gateway.newClient(Naming.QUERY)) {
			return Query.executeCountQuery(client, schema, target -> buildFilters(target, entity.getName(), filterBy.values()));
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[QueryCollector.count]", e);
			return 0;
		}
	}
}
