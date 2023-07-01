package epf.portlet.schema;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.portlet.persistence.SchemaView;
import epf.client.util.Client;
import epf.persistence.schema.Entity;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.naming.Naming;
import epf.portlet.internal.security.SecurityUtil;
import epf.portlet.util.EventUtil;
import epf.portlet.util.ParameterUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Schema.SCHEMA)
public class Schema implements SchemaView, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Schema.class.getName());
	
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
	private transient GatewayUtil gatewayUtil;
	
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
	@PostConstruct
	protected void postConstruct() {
		try {
			entities = fetchEntities();
			entity = paramUtil.getValue(Naming.Parameter.SCHEMA_ENTITY);
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
		final URI schemaUrl = gatewayUtil.get(epf.naming.Naming.SCHEMA);
		try(Client client = clientUtil.newClient(schemaUrl)){
			try(Response response = epf.persistence.client.Schema.getEntities(client)){
				return response.readEntity(new GenericType<List<Entity>>() {});
			}
		}
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
	@Override
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	@Override
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
			eventUtil.setEvent(Naming.Event.SCHEMA_ENTITY, selectedEntity.get());
		}
		paramUtil.setValue(Naming.Parameter.SCHEMA_ENTITY, entity);
	}
}
