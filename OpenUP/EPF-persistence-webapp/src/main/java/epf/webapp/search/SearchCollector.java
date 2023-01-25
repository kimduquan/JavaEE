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
import epf.client.util.Client;
import epf.naming.Naming.Query;
import epf.query.client.Search;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
import epf.webapp.internal.GatewayUtil;

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
        try(Client client = gateway.newClient(Query.SEARCH)) {
			client.authorization(token);
			try(Response response = Search.search(client, text, first, pageSize)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					return JsonUtil.readArray(stream)
							.stream()
							.map(value -> value.asJsonObject())
							.collect(Collectors.toList());
				}
			}
        } 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SearchCollector.entities]", e);
		}
		return Arrays.asList();
    }

    @Override
    public JsonObject getRowData(final String rowKey) {
        return entities.get(Integer.parseInt(rowKey));
    }

    @Override
    public String getRowKey(final JsonObject object) {
        return String.valueOf(entities.indexOf(object));
    }

	@Override
	public int count(final Map<String, FilterMeta> filterBy) {
		return 0;
	}
}
