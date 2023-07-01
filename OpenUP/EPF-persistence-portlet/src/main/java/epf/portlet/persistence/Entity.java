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
import javax.json.JsonValue;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.persistence.schema.Embeddable;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.internal.persistence.EntityUtil;
import epf.portlet.internal.security.SecurityUtil;
import epf.portlet.naming.Naming;
import epf.portlet.util.EventUtil;
import epf.portlet.util.ParameterUtil;
import epf.portlet.util.RequestUtil;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Persistence.PERSISTENCE_ENTITY)
public class Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Entity.class.getName());
	
	/**
	 * 
	 */
	private epf.persistence.schema.client.Entity entity;
	
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
	private Map<String, epf.persistence.schema.client.Entity> entities;
	
	/**
	 * 
	 */
	private final Map<String, List<JsonObject>> associationValues = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityUtil securityUtil;

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
	@Inject
	private transient EntityUtil entityUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		entity = eventUtil.getEvent(Naming.Event.SCHEMA_ENTITY);
		if(entity != null) {
			try {
				entities = fetchEntities()
						.stream()
						.collect(Collectors.toMap(epf.persistence.schema.client.Entity::getType, e -> e));
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
	protected List<Embeddable> fetchEmbeddables() throws Exception{
		try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.SCHEMA))){
			try(Response response = epf.persistence.schema.client.Schema.getEmbeddables(client)){
				return response.readEntity(new GenericType<List<Embeddable>>() {});
			}
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected List<epf.persistence.schema.client.Entity> fetchEntities() throws Exception{
		try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.SCHEMA))){
			try(Response response = epf.persistence.schema.client.Schema.getEntities(client)){
				return response.readEntity(new GenericType<List<epf.persistence.schema.client.Entity>>() {});
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
					.object(object)
					.entities(entities)
					.embeddables(embeddables);
			attributes = entity.getAttributes()
					.stream()
					.map(attribute -> attrBuilder.attribute(attribute).build())
					.collect(Collectors.toList());
		}
	}
	
	/**
	 * @return
	 */
	public boolean preLoad() {
		if(entity != null && object == null) {
			try {
				id = requestUtil.getRequest().getRenderParameters().getValue(Naming.Parameter.PERSISTENCE_ENTITY_ID);
				if(id != null && !id.isEmpty()) {
					object = new EntityObject(entityUtil.getEntity(entity.getTable().getSchema(), entity.getName(), id));
					final AttributeBuilder attrBuilder = new AttributeBuilder()
							.object(object)
							.entities(entities)
							.embeddables(embeddables);
					attributes = entity.getAttributes()
							.stream()
							.map(attribute -> attrBuilder.attribute(attribute).build())
							.collect(Collectors.toList());
					attributes
					.parallelStream()
					.filter(attr -> attr.getAttribute().isAssociation())
					.forEach(attr -> {
						final String attrType = attr.getAttribute().getBindableType();
						final epf.persistence.schema.client.Entity entity = entities.get(attrType);
						associationValues.computeIfAbsent(attrType, type -> {
							try {
								return entityUtil.getEntities(entity.getTable().getSchema(), entity.getName(), null, null);
							} 
							catch (Exception e) {
								LOGGER.throwing(getClass().getName(), "preLoad", e);
							}
							return Arrays.asList();
						});
					});
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
	public List<Identifiable> getAssociationValues(final BasicAttribute attribute){
		if(attribute.getAttribute().isAssociation()) {
			final String attrType = attribute.getAttribute().getBindableType();
			final epf.persistence.schema.client.Entity entity = entities.get(attrType);
			return associationValues.computeIfAbsent(attrType, type -> {
						try {
							return entityUtil.getEntities(entity.getTable().getSchema(), entity.getName(), null, null);
						} 
						catch (Exception e) {
							LOGGER.throwing(getClass().getName(), "getAssociationValues", e);
						}
						return Arrays.asList();
					})
					.stream()
					.map(obj -> new Identifiable(obj, entity))
					.collect(Collectors.toList());
		}
		return Arrays.asList();
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void persist() throws Exception {
		try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.PERSISTENCE))){
			try(Response response = epf.persistence.client.Entities.persist(
					client, 
					entity.getTable().getSchema(),
					entity.getName(), 
					object.merge().toString()
					)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					object = new EntityObject(JsonUtil.readObject(stream));
				}
			}
		}
		if(entity.getId() != null) {
			final JsonValue idValue = object.get(entity.getId().getName());
			id = JsonUtil.toString(idValue);
			if(id != null) {
				paramUtil.setValue(Naming.Parameter.PERSISTENCE_ENTITY_ID, id);
			}
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void merge() throws Exception {
		try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.PERSISTENCE))){
			epf.persistence.client.Entities.merge(
					client,
					entity.getTable().getSchema(),
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
		try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.PERSISTENCE))){
			epf.persistence.client.Entities.remove(
					client, 
					entity.getTable().getSchema(),
					entity.getName(), 
					id
					);
		}
		return "persistence";
	}

	public epf.persistence.schema.client.Entity getEntity() {
		return entity;
	}
}
