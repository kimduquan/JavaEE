/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.schema.Attribute;
import epf.client.schema.Entity;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Naming;
import epf.portlet.Parameter;
import epf.portlet.ParameterUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.config.ConfigUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.PERSISTENCE_QUERY)
public class Query implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Persistence.class.getName());
	
	/**
	 * 
	 */
	private Entity entity;
	
	/**
	 * 
	 */
	private List<Attribute> attributes;
	
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
	private List<Map<String, Object>> result;
	
	/**
	 * 
	 */
	@Inject
	private transient RegistryUtil registryUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigUtil configUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient EventUtil eventUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ParameterUtil paramUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		entity = eventUtil.getEvent(Event.SCHEMA_ENTITY);
		if(entity != null) {
			attributes = entity
					.getAttributes()
					.stream()
					.filter(AttributeUtil::isBasic)
					.collect(Collectors.toList());
		}
		try {
			firstResult = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT));
			maxResults = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT));
			if(entity != null) {
				result = getResultList();
			}
		}
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, Object>> getResultList() throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Queries.executeQuery(
					client, 
					path -> path.path(entity.getName()), 
					firstResult, 
					maxResults)){
				return response.readEntity(new GenericType<List<Map<String, Object>>>() {});
			}
		}
	}
	
	/**
	 * @param object
	 * @return
	 */
	public int indexOf(final Map<String, Object> object) {
		return firstResult + result.indexOf(object);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void executeQuery() throws Exception {
		if(entity != null) {
			result = getResultList();
		}
	}
	
	/**
	 * @param object
	 * @return
	 */
	public String merge(final Map<String, Object> object) {
		if(entity.isSingleId()) {
			final Attribute id = entity.getId();
			if(id != null) {
				final Object idValue = object.get(id.getName());
				if(idValue != null) {
					paramUtil.setValue(Parameter.PERSISTENCE_ENTITY_ID, idValue.toString());
				}
			}
		}
		return "entity";
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(final int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(final int maxResults) {
		this.maxResults = maxResults;
	}
}
