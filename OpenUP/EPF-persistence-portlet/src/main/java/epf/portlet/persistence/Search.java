package epf.portlet.persistence;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.client.portlet.persistence.SearchView;
import epf.portlet.internal.config.ConfigUtil;
import epf.portlet.naming.Naming;
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
	private transient ConfigUtil configUtil;
	
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
