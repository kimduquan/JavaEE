/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import epf.client.schema.Entity;
import epf.portlet.Name;
import epf.portlet.ParameterUtil;
import epf.portlet.schema.SchemaUtil;
import epf.schema.EPF;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Name.PERSISTENCE)
public class Persistence implements Serializable {

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
	private transient SchemaUtil schemaUtil;
	
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
			entities = schemaUtil.getEntities(EPF.SCHEMA);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
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
	 * @param event
	 */
	public void handleEntityChanged(final AjaxBehaviorEvent event) {
		paramUtil.setValue("entity", entity);
	}

	/**
	 * @return the objects
	 */
	public List<JsonObject> getObjects() {
		return objects;
	}
}
