package epf.webapp.search;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import epf.client.util.Client;
import epf.naming.Naming.Query;
import epf.query.client.EntityId;
import epf.query.client.Search;
import epf.util.StringUtil;
import epf.util.logging.LogManager;
import epf.webapp.internal.GatewayUtil;

/**
 * 
 */
public class SearchCollector extends LazyDataModel<EntityId> {

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
	private final Map<String, EntityId> entityIds = new ConcurrentHashMap<>();
	
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
	public List<EntityId> load(final int first, final int pageSize, final Map<String, SortMeta> sortBy, final Map<String, FilterMeta> filterBy) {
        try(Client client = gateway.newClient(Query.SEARCH)) {
			client.authorization(token);
			entityIds.clear();
			return Search.search(client, text, first, pageSize);
        } 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SearchCollector.entities]", e);
		}
		return Arrays.asList();
    }

    @Override
    public EntityId getRowData(final String rowKey) {
    	return entityIds.get(rowKey);
    }

    @Override
    public String getRowKey(final EntityId object) {
        final String key = StringUtil.join(object.getSchema(), object.getName(), object.getAttributes().values().iterator().next().toString());
        entityIds.put(key, object);
        return key;
    }

	@Override
	public int count(final Map<String, FilterMeta> filterBy) {
		try(Client client = gateway.newClient(Query.SEARCH)) {
			client.authorization(token);
			return Search.count(client, text);
        } 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SearchCollector.count]", e);
		}
		return 0;
	}
}
