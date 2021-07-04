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
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;
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
	private List<EntityAttribute> attributes;
	
	/**
	 * 
	 */
	private EntityObject object;
	
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
	
	/**
	 * 
	 */
	public void prePersist() {
		if(entity != null && object == null) {
			final JsonObjectBuilder builder = Json.createObjectBuilder();
			entity.getAttributes().forEach(attribute -> AttributeUtil.addDefault(builder, attribute));
			object = new EntityObject(builder.build());
			attributes = entity.getAttributes()
					.stream()
					.map(attribute -> new EntityAttribute(object, attribute))
					.collect(Collectors.toList());
		}
	}
	
	/**
	 * @return
	 */
	public boolean preLoad() {
		if(entity != null && object == null) {
			try {
				id = requestUtil.getRequest().getRenderParameters().getValue(Parameter.PERSISTENCE_ENTITY_ID);
				if(id != null && !id.isEmpty()) {
					object = new EntityObject(fetchEntity());
					attributes = entity.getAttributes()
							.stream()
							.map(attribute -> new EntityAttribute(object, attribute))
							.collect(Collectors.toList());
				}
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "postConstruct", e);
			}
		}
		return entity != null && object != null;
	}
	
	public EntityObject getObject() {
		return object;
	}

	public List<EntityAttribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void persist() throws Exception {
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Entities.persist(
					client, 
					entity.getName(), 
					object.merge().toString()
					)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						object = new EntityObject(reader.readObject());
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
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			epf.client.persistence.Entities.merge(
					client,
					entity.getName(), 
					id, 
					object.merge().toString()
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
