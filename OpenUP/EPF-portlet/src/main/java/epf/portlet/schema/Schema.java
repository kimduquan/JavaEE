/**
 * 
 */
package epf.portlet.schema;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.schema.Entity;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Name;
import epf.portlet.Parameter;
import epf.portlet.ParameterUtil;
import epf.portlet.SessionUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.portlet.security.Principal;
import epf.schema.EPF;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Name.SCHEMA)
public class Schema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Schema.class.getName());
	
	/**
	 * 
	 */
	private List<Entity> entities;
	
	/**
	 * 
	 */
	private List<JsonObject> objects;
	
	/**
	 * 
	 */
	private String entity;
	
	/**
	 * 
	 */
	@Inject
	private transient SessionUtil sessionUtil;
	
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
	private transient ParameterUtil paramUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			entities = getEntities(EPF.SCHEMA);
			entity = paramUtil.getValue(Parameter.SCHEMA_ENTITY);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	protected List<Entity> getEntities(final String unit) throws Exception{
		final Principal principal = sessionUtil.getAttribute(Name.SECURITY_PRINCIPAL);
		if(principal != null) {
			final URI schemaUrl = registryUtil.get("schema");
			try(Client client = clientUtil.newClient(schemaUrl)){
				client.authorization(principal.getToken().getRawToken());
				try(Response response = epf.client.schema.Schema.getEntities(client, unit)){
					return response.readEntity(new GenericType<List<Entity>>() {});
				}
			}
		}
		return null;
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public List<Entity> getEntities(){
		return entities;
	}

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(final String entity) {
		this.entity = entity;
	}
	
	/**
	 * 
	 */
	public void handleEntityChanged(final ValueChangeEvent event) {
		entity = event.getNewValue().toString();
		paramUtil.setValue("entity", event.getNewValue().toString());
		eventUtil.setEvent(Event.SCHEMA_ENTITY, new epf.portlet.schema.Entity());
	}

	/**
	 * @return the objects
	 */
	public List<JsonObject> getObjects() {
		return objects;
	}
}
