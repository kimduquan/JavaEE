package epf.webapp.search;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import epf.client.query.Query;
import epf.client.schema.EntityId;
import epf.client.search.Search;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;

/**
 * 
 */
public class SearchCollector extends LazyDataModel<JsonObject> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(SearchCollector.class.getName());
	
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
	private final String text;
	
	/**
	 *
	 */
	private List<JsonObject> entities = Arrays.asList();
	
	/**
	 * @param gateway
	 * @param token
	 * @param text
	 */
	public SearchCollector(final GatewayUtil gateway, final char[] token, final String text) {
		this.gateway = gateway;
		this.token = token;
		this.text = text;
	}

	@Override
	public List<JsonObject> load(final int first, final int pageSize, final Map<String, SortMeta> sortBy, final Map<String, FilterMeta> filterBy) {
        try(Client client = gateway.newClient(Naming.SEARCH)) {
			client.authorization(token);
			final Integer count = Search.count(client, text);
			setRowCount(count);
			final List<EntityId> entityIds = Search.search(client, text, first, pageSize);
			try(Client client2 = gateway.newClient(Naming.QUERY)){
				try(Response response = Query.fetchEntities(client2, entityIds)){
					try(InputStream stream = response.readEntity(InputStream.class)){
						entities = JsonUtil.readArray(stream)
								.stream()
								.map(value -> value.asJsonObject())
								.collect(Collectors.toList());
					}
				}
			}
        } 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SearchCollector.entities]", e);
		}
		return entities;
    }

    @Override
    public JsonObject getRowData(final String rowKey) {
        return entities.get(Integer.parseInt(rowKey));
    }

    @Override
    public Object getRowKey(final JsonObject object) {
        return entities.indexOf(object);
    }
}
