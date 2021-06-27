/**
 * 
 */
package epf.portlet.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import epf.client.schema.Attribute;
import epf.client.security.Token;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Naming;
import epf.portlet.SessionUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Naming.PERSISTENCE_ENTITY)
public class Entity {
	
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
	private Map<String, Object> object;
	
	/**
	 * 
	 */
	private Attribute id;
	
	/**
	 * 
	 */
	private Token token;
	
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
	private transient SessionUtil sessionUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		entity = eventUtil.getEvent(Event.SCHEMA_ENTITY);
		if(entity != null) {
			attributes = entity.getAttributes();
			object = new HashMap<>();
			attributes.forEach(attribute -> object.put(attribute.getName(), null));
			id = entity.getId();
		}
		token = sessionUtil.getAttribute(Naming.SECURITY_TOKEN);
		if(entity != null && id != null && token != null) {
			try(Client client = clientUtil.newClient(registryUtil.get("cache"))){
				
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "postConstruct", e);
			}
		}
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public Map<String, Object> getObject() {
		return object;
	}

	public Attribute getId() {
		return id;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void persist() throws Exception {
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			final JsonObjectBuilder builder = Json.createObjectBuilder(object);
			client.authorization(token.getRawToken());
			epf.client.persistence.Entities.persist(
					client, 
					entity.getName(), 
					builder.build().toString()
					);
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void merge() throws Exception {
		if(id != null) {
			final Object idValue = object.get(id.getName());
			if(idValue != null) {
				final JsonObjectBuilder builder = Json.createObjectBuilder(object);
				try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
					client.authorization(token.getRawToken());
					epf.client.persistence.Entities.merge(
							client,
							entity.getName(), 
							idValue.toString(), 
							builder.build().toString()
							);
				}
			}
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void remove() throws Exception {
		if(id != null) {
			final Object idValue = object.get(id.getName());
			if(idValue != null) {
				try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
					client.authorization(token.getRawToken());
					epf.client.persistence.Entities.remove(
							client, 
							entity.getName(), 
							idValue.toString()
							);
				}
			}
		}
	}
}
