/**
 * 
 */
package epf.portlet.persistence;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.schema.Attribute;
import epf.client.schema.Entity;
import epf.client.security.Token;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Naming;
import epf.portlet.Parameter;
import epf.portlet.ParameterUtil;
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
@Named(Naming.PERSISTENCE)
public class Persistence {
	
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
	private List<Map<String, Object>> objects;
	
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
		token = sessionUtil.getAttribute(Naming.SECURITY_TOKEN);
		if(entity != null && token != null) {
			try{
				objects = fetchObjects();
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
	protected List<Map<String, Object>> fetchObjects() throws Exception{
		try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
			client.authorization(token.getRawToken());
			try(Response response = epf.client.persistence.Queries.executeQuery(
					client, 
					path -> path.path(entity.getName()), 
					0, 
					100)){
				return response.readEntity(new GenericType<List<Map<String, Object>>>() {});
			}
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public List<Map<String, Object>> getObjects() {
		return objects;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * @param object
	 * @throws Exception
	 */
	public void remove(final Map<String, Object> object) throws Exception {
		if(entity.isSingleId()) {
			final Attribute id = entity.getId();
			if(id != null) {
				final Object idValue = object.get(id.getName());
				if(idValue != null) {
					try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
						client.authorization(token.getRawToken());
						epf.client.persistence.Entities.remove(client, entity.getName(), idValue.toString());
					}
				}
			}
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
}
