/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.persistence.SearchData;
import epf.client.portlet.persistence.SearchView;
import epf.portlet.config.ConfigUtil;
import epf.portlet.gateway.GatewayUtil;
import epf.portlet.security.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@Named(Naming.PERSISTENCE_SEARCH)
@ViewScoped
public class Search implements SearchView, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Search.class.getName());
	
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
	private List<SearchData> resultList;
	
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
			firstResult = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT));
			maxResults = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT));
		}
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	@Override
	public void search() throws Exception{
		try(Client client = clientUtil.newClient(gatewayUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Queries.search(client, text, firstResult, maxResults)){
				resultList = response.readEntity(new GenericType<List<SearchData>>() {});
			}
		}
	}
	
	/**
	 * @param object
	 * @return
	 */
	public int indexOf(final SearchData data) {
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
