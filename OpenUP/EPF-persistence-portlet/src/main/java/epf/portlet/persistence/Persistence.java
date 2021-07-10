/**
 * 
 */
package epf.portlet.persistence;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;
import epf.client.schema.Attribute;
import epf.client.schema.Entity;
import epf.portlet.Event;
import epf.portlet.EventUtil;
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
@Named(Naming.PERSISTENCE)
public class Persistence implements Serializable {
	
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
	private List<JsonObject> objects;
	
	/**
	 * 
	 */
	private int firstResult;
	
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
			try {
				firstResult = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT));
				final int maxResults = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT));
				objects = fetchObjects(firstResult, maxResults);
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
	protected List<JsonObject> fetchObjects(final int firstResult, final int maxResults) throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Queries.executeQuery(
					client, 
					path -> path.path(entity.getName()), 
					firstResult, 
					maxResults)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						return reader
						.readArray()
						.stream()
						.map(value -> value.asJsonObject())
						.collect(Collectors.toList());
					}
				}
			}
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public List<JsonObject> getObjects() {
		return objects;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * @param object
	 * @throws Exception
	 */
	public void remove(final JsonObject object) throws Exception {
		if(entity.isSingleId()) {
			final Attribute id = entity.getId();
			if(id != null) {
				final JsonValue idValue = object.get(id.getName());
				if(idValue != null) {
					final String value = JsonUtil.toString(idValue);
					try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
						epf.client.persistence.Entities.remove(client, entity.getName(), value);
					}
				}
			}
		}
	}
	
	/**
	 * @param object
	 * @return
	 */
	public String merge(final JsonObject object) {
		if(entity.isSingleId()) {
			final Attribute id = entity.getId();
			if(id != null) {
				final JsonValue idValue = object.get(id.getName());
				if(idValue != null) {
					final String value = JsonUtil.toString(idValue);
					paramUtil.setValue(Parameter.PERSISTENCE_ENTITY_ID, value);
				}
			}
		}
		return "entity";
	}
	
	/**
	 * @param object
	 * @return
	 */
	public int indexOf(final JsonObject object) {
		return firstResult + objects.indexOf(object);
	}
}
