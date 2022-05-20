package epf.portlet.persistence;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.client.portlet.persistence.SearchView;
import epf.client.query.QueryUtil;
import epf.client.schema.EntityId;
import epf.client.util.Client;
import epf.portlet.internal.config.ConfigUtil;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.naming.Naming;
import epf.portlet.internal.security.SecurityUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@Named(Naming.Persistence.PERSISTENCE_SEARCH)
@ViewScoped
public class Search implements SearchView, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Search.class.getName());
	
	/**
	 * 
	 */
	private int firstResult;
	
	/**
	 * 
	 */
	private int maxResults;
	
	/**
	 * 
	 */
	private String text;
	
	/**
	 * 
	 */
	private List<URI> resultList;
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigUtil configUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityUtil clientUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			firstResult = Integer.valueOf(configUtil.getProperty(epf.naming.Naming.Query.FIRST_RESULT_DEFAULT));
			maxResults = Integer.valueOf(configUtil.getProperty(epf.naming.Naming.Query.MAX_RESULTS_DEFAULT));
		}
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	@Override
	public void search() throws Exception{
		try(Client client = clientUtil.newClient(gatewayUtil.get(epf.naming.Naming.SEARCH))){
			final List<EntityId> entityIds = epf.client.search.Search.search(client, text, firstResult, maxResults);
			resultList = new ArrayList<>();
			final URI baseUrl = gatewayUtil.get(epf.naming.Naming.QUERY);
			for(EntityId entityId : entityIds) {
				resultList.add(QueryUtil.fromEntityId(baseUrl, entityId).build());
			}
		}
	}
	
	/**
	 * @param object
	 * @return
	 */
	public int indexOf(final URI data) {
		return firstResult + resultList.indexOf(data);
	}

	@Override
	public List<?> getResultList() {
		return resultList;
	}

	@Override
	public int getFirstResult() {
		return firstResult;
	}

	@Override
	public void setFirstResult(final int firstResult) {
		this.firstResult = firstResult;
	}

	@Override
	public int getMaxResults() {
		return maxResults;
	}

	@Override
	public void setMaxResults(final int maxResults) {
		this.maxResults = maxResults;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(final String text) {
		this.text = text;
	}
}
