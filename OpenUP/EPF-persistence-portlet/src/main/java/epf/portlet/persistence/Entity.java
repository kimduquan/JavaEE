/**
 * 
 */
package epf.portlet.persistence;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;
import epf.client.schema.Attribute;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Parameter;
import epf.portlet.ParameterUtil;
import epf.portlet.RequestUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.PERSISTENCE_ENTITY)
public class Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Entity.class.getName());
	
	/**
	 * 
	 */
	private epf.client.schema.Entity entity;
	
	/**
	 * 
	 */
	private List<Attribute> attributes;
	
	/**
	 * 
	 */
	private JsonObject object;
	
	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	@Inject
	private transient RegistryUtil registryUtil;
	
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
	private transient RequestUtil requestUtil;
	
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
		try {
			id = requestUtil.getRequest().getRenderParameters().getValue(Parameter.PERSISTENCE_ENTITY_ID);
		}
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
		if(entity != null) {
			attributes = entity.getAttributes();
		}
		if(entity != null && id == null) {
			final JsonObjectBuilder builder = Json.createObjectBuilder();
			attributes.forEach(attribute -> AttributeUtil.addDefault(builder, attribute));
			object = builder.build();
		}
		if(entity != null && id != null) {
			try {
				object = fetchEntity();
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
	protected JsonObject fetchEntity() throws Exception {
		try{
			return fetchCachedEntity();
		} 
		catch (Exception e) {
			return fetchPersistedEntity();
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected JsonObject fetchCachedEntity() throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("cache"))){
			try(Response response = epf.client.cache.Cache.getEntity(client, entity.getName(), id)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						return reader.readObject();
					}
				}
			}
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected JsonObject fetchPersistedEntity() throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Entities.find(client, entity.getName(), id)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						return reader.readObject();
					}
				}
			}
		}
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public JsonObject getObject() {
		return object;
	}

	public String getId() {
		try {
			id = requestUtil.getRequest().getRenderParameters().getValue(Parameter.PERSISTENCE_ENTITY_ID);
		}
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
		return id;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void persist() throws Exception {
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			final JsonObjectBuilder builder = Json.createObjectBuilder(object);
			try(Response response = epf.client.persistence.Entities.persist(
					client, 
					entity.getName(), 
					builder.build().toString()
					)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						object = reader.readObject();
					}
				}
			}
		}
		if(entity.getId() != null) {
			final JsonValue idValue = object.get(entity.getId().getName());
			if(idValue != null) {
				id = AttributeUtil.getAsString(idValue);
				paramUtil.setValue(Parameter.PERSISTENCE_ENTITY_ID, id);
			}
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void merge() throws Exception {
		final JsonObjectBuilder builder = Json.createObjectBuilder(object);
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			epf.client.persistence.Entities.merge(
					client,
					entity.getName(), 
					id, 
					builder.build().toString()
					);
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public String remove() throws Exception {
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			epf.client.persistence.Entities.remove(
					client, 
					entity.getName(), 
					id
					);
		}
		return "persistence";
	}
}
