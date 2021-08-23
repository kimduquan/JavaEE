/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.JsonValue;
import epf.client.portlet.persistence.QueryView;
import epf.client.schema.Attribute;
import epf.client.schema.Entity;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.JsonObjectCollector;
import epf.portlet.JsonUtil;
import epf.portlet.Parameter;
import epf.portlet.ParameterUtil;
import epf.portlet.config.ConfigUtil;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.PERSISTENCE_QUERY)
public class Query implements QueryView, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Query.class.getName());
	
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
	private List<JsonObject> resultList;
	
	/**
	 * 
	 */
	private JsonObjectCollector collector;
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigUtil configUtil;
	
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
	@Inject
	private transient EntityUtil entityUtil;
	
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
					.collect(Collectors.toList());
			collector = new JsonObjectCollector(attributes.stream().map(Attribute::getName).collect(Collectors.toList()));
			try {
				firstResult = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT));
				maxResults = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT));
				resultList = entityUtil.getEntities(entity.getName(), firstResult, maxResults);
			}
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "postConstruct", e);
			}
		}
	}
	
	@Override
	public int getIndexOf(final String attribute, final Object object) {
		return firstResult + resultList.indexOf(object);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void executeQuery() throws Exception {
		if(entity != null) {
			attributes = entity
					.getAttributes()
					.stream()
					.collect(Collectors.toList());
			collector = new JsonObjectCollector(attributes.stream().map(Attribute::getName).collect(Collectors.toList()));
			resultList = entityUtil.getEntities(entity.getName(), firstResult, maxResults);
		}
	}
	
	@Override
	public String merge(final String attribute, final Object ent) {
		final JsonObject object = (JsonObject) ent;
		if(entity.isSingleId()) {
			final Attribute id = entity.getId();
			if(id != null) {
				final JsonValue idValue = object.get(id.getName());
				final String value = JsonUtil.toString(idValue);
				if(value != null) {
					paramUtil.setValue(Parameter.PERSISTENCE_ENTITY_ID, value);
				}
			}
		}
		return "entity";
	}

	public List<Attribute> getAttributes() {
		return attributes;
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
	public int getResultSize() {
		return resultList.size();
	}

	@Override
	public List<?> getResultList(final String attribute) {
		return collector.collect(resultList.stream());
	}

	@Override
	public void filter(final String attribute, final String value) throws Exception {
		collector.filter(attribute, value);
	}

	@Override
	public void sort(final String attribute) throws Exception {
		collector.sort(attribute);
	}

	@Override
	public void move(final String attribute) {
		final Attribute attr = attributes.stream().filter(a -> a.getName().equals(attribute)).findFirst().get();
		final int index = attributes.indexOf(attr);
		final Attribute prevAttr = attributes.get(index - 1);
		attributes.set(index, prevAttr);
		attributes.set(index - 1, attr);
		
		collector.move(attribute);
	}
}
