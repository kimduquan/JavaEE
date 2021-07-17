/**
 * 
 */
package epf.portlet.persistence;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;
import epf.client.portlet.persistence.QueryView;
import epf.client.schema.Attribute;
import epf.client.schema.Entity;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.JsonObjectComparator;
import epf.portlet.JsonObjectFilter;
import epf.portlet.JsonUtil;
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
public class Query implements QueryView, Serializable {

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
	private List<JsonObject> resultList;
	
	/**
	 * 
	 */
	private List<JsonObjectComparator> comparators;
	
	/**
	 * 
	 */
	private List<JsonObjectFilter> filters;
	
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
					.collect(Collectors.toList());
			comparators = Arrays.asList(new JsonObjectComparator[attributes.size()]);
			filters = Arrays.asList(new JsonObjectFilter[attributes.size()]);
			try {
				firstResult = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT));
				maxResults = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT));
				resultList = getResultList();
			}
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "postConstruct", e);
			}
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected List<JsonObject> getResultList() throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Queries.executeQuery(
					client, 
					path -> path.path(entity.getName()), 
					firstResult, 
					maxResults)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						Stream<JsonObject> streamObj = reader
								.readArray()
								.stream()
								.map(value -> value.asJsonObject());
						for(JsonObjectFilter filter : filters) {
							if(filter != null) {
								streamObj = streamObj.filter(filter::filter);
							}
						}
						for(JsonObjectComparator comparator : comparators) {
							if(comparator != null) {
								streamObj = streamObj.sorted(comparator);
							}
						}
						return streamObj.collect(Collectors.toList());
					}
				}
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
			resultList = getResultList();
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
		Stream<JsonObject> streamObj = resultList.stream();
		for(JsonObjectFilter filter : filters) {
			if(filter != null) {
				streamObj = streamObj.filter(filter::filter);
			}
		}
		for(JsonObjectComparator comparator : comparators) {
			if(comparator != null) {
				streamObj = streamObj.sorted(comparator);
			}
		}
		return streamObj.collect(Collectors.toList());
	}

	@Override
	public void filter(final Object attribute, final Object value) throws Exception {
		final Attribute attr = (Attribute) attribute;
		final int index = attributes.indexOf(attr);
		if(filters.get(index) == null) {
			filters.set(index, new JsonObjectFilter(attr.getName()));
		}
	}

	@Override
	public void sort(final Object attribute) throws Exception {
		final Attribute attr = (Attribute) attribute;
		final int index = attributes.indexOf(attr);
		JsonObjectComparator comparator = comparators.get(index);
		if(comparator == null) {
			comparator = new JsonObjectComparator(attr.getName());
			comparators.set(index, comparator);
		}
		else {
			comparator.setAscending(!comparator.isAscending());
		}
	}
}
