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
import epf.client.portlet.persistence.PersistenceView;
import epf.client.schema.Attribute;
import epf.client.schema.Entity;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.JsonUtil;
import epf.portlet.Parameter;
import epf.portlet.ParameterUtil;
import epf.portlet.config.ConfigUtil;
import epf.portlet.gateway.GatewayUtil;
import epf.portlet.security.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.PERSISTENCE)
public class Persistence implements PersistenceView, Serializable {
	
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
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigUtil configUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityUtil clientUtil;
	
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
					.filter(AttributeUtil::isBasic)
					.collect(Collectors.toList());
			try {
				firstResult = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT));
				final int maxResults = Integer.valueOf(configUtil.getProperty(epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT));
				objects = entityUtil.getEntities(entity.getTable().getSchema(), entity.getName(), firstResult, maxResults);
			}
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "postConstruct", e);
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
	
	@Override
	public void remove(final Object ent) throws Exception {
		final JsonObject object = (JsonObject) ent;
		if(entity.isSingleId()) {
			final Attribute id = entity.getId();
			if(id != null) {
				final JsonValue idValue = object.get(id.getName());
				if(idValue != null) {
					final String value = JsonUtil.toString(idValue);
					try(Client client = clientUtil.newClient(gatewayUtil.get("persistence"))){
						epf.client.persistence.Entities.remove(client, entity.getTable().getSchema(), entity.getName(), value);
					}
				}
			}
		}
	}
	
	@Override
	public String merge(final Object ent) {
		final JsonObject object = (JsonObject) ent;
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
	
	@Override
	public int indexOf(final Object ent) {
		final JsonObject object = (JsonObject) ent;
		return firstResult + objects.indexOf(object);
	}
}
