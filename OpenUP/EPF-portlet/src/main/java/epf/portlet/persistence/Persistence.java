/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import epf.client.schema.Entity;
import epf.portlet.Name;
import epf.portlet.Parameter;
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
			entity = paramUtil.getValue(Parameter.PERSISTENCE_ENTITY);
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
	 * 
	 */
	public void handleEntityChanged(final ValueChangeEvent event) {
		paramUtil.setValue("entity", event.getNewValue().toString());
	}

	/**
	 * @return the objects
	 */
	public List<JsonObject> getObjects() {
		return objects;
	}
}
