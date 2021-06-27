/**
 * 
 */
package epf.portlet.schema;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
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
@Named(Naming.SCHEMA)
public class Schema {
	
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
			entities = fetchEntities();
			entity = paramUtil.getValue(Parameter.SCHEMA_ENTITY);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected List<Entity> fetchEntities() throws Exception{
		final Token token = sessionUtil.getAttribute(Naming.SECURITY_TOKEN);
		if(token != null) {
			final URI schemaUrl = registryUtil.get("schema");
			try(Client client = clientUtil.newClient(schemaUrl)){
				client.authorization(token.getRawToken());
				try(Response response = epf.client.schema.Schema.getEntities(client)){
					return response.readEntity(new GenericType<List<Entity>>() {});
				}
			}
		}
		return null;
	}
	
	/**
	 * @return
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
		final Optional<Entity> selectedEntity = entities.stream().filter(e -> e.getName().equals(entity)).findFirst();
		if(selectedEntity.isPresent()) {
			eventUtil.setEvent(Event.SCHEMA_ENTITY, selectedEntity.get());
		}
		paramUtil.setValue(Parameter.SCHEMA_ENTITY, entity);
	}
}
