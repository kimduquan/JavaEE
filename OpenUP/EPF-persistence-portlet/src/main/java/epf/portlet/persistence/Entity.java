/**
 * 
 */
package epf.portlet.persistence;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.schema.Embeddable;
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
	private List<BasicAttribute> attributes;
	
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
	private Map<String, Embeddable> embeddables;
	
	/**
	 * 
	 */
	private Map<String, epf.client.schema.Entity> entities;
	
	/**
	 * 
	 */
	private final Map<String, List<JsonObject>> values = new ConcurrentHashMap<>();
	
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
		if(entity != null) {
			try {
				entities = fetchEntities()
						.stream()
						.collect(Collectors.toMap(epf.client.schema.Entity::getType, e -> e));
				embeddables = fetchEmbeddables()
						.stream()
						.collect(Collectors.toMap(Embeddable::getType, e -> e));
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
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected List<Embeddable> fetchEmbeddables() throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("schema"))){
			try(Response response = epf.client.schema.Schema.getEmbeddables(client)){
				return response.readEntity(new GenericType<List<Embeddable>>() {});
			}
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected List<epf.client.schema.Entity> fetchEntities() throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("schema"))){
			try(Response response = epf.client.schema.Schema.getEmbeddables(client)){
				return response.readEntity(new GenericType<List<epf.client.schema.Entity>>() {});
			}
		}
	}
	
	/**
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected List<JsonObject> fetchValues(final String type) throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			try(Response response = epf.client.persistence.Queries.executeQuery(
					client, 
					path -> path.path(entity.getName()), 
					null, 
					null)){
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
	
	/**
	 * 
	 */
	public void prePersist() {
		if(entity != null && object == null) {
			final JsonObjectBuilder builder = Json.createObjectBuilder();
			entity.getAttributes().forEach(attribute -> AttributeUtil.addDefault(builder, attribute));
			object = new EntityObject(builder.build());
			final AttributeBuilder attrBuilder = new AttributeBuilder()
					.setObject(object)
					.setEntities(entities)
					.setEmbeddables(embeddables);
			attributes = entity.getAttributes()
					.stream()
					.map(attribute -> attrBuilder.setAttribute(attribute).build())
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
					final AttributeBuilder attrBuilder = new AttributeBuilder()
							.setObject(object)
							.setEntities(entities)
							.setEmbeddables(embeddables);
					attributes = entity.getAttributes()
							.stream()
							.map(attribute -> attrBuilder.setAttribute(attribute).build())
							.collect(Collectors.toList());
				}
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "preLoad", e);
			}
		}
		return entity != null && object != null;
	}
	
	public EntityObject getObject() {
		return object;
	}

	public List<BasicAttribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * @param attribute
	 * @return
	 */
	public EmbeddedAttribute getEmbeddedAttribute(final BasicAttribute attribute){
		if(attribute instanceof EmbeddedAttribute) {
			return (EmbeddedAttribute) attribute;
		}
		return null;
	}
	
	/**
	 * @param attribute
	 * @return
	 */
	public List<JsonObject> getValues(final BasicAttribute attribute){
		if(attribute.getAttribute().isAssociation()) {
			return values.computeIfAbsent(attribute.getAttribute().getType(), type -> {
				try {
					return fetchValues(type);
				} 
				catch (Exception e) {
					LOGGER.throwing(getClass().getName(), "getValues", e);
				}
				return null;
			});
		}
		return Arrays.asList();
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
			id = AttributeUtil.getAsString(idValue);
			if(id != null) {
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

	public epf.client.schema.Entity getEntity() {
		return entity;
	}
}
