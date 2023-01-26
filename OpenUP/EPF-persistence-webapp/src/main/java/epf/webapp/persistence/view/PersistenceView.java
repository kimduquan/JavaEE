package epf.webapp.persistence.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.json.JsonObject;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Persistence.VIEW)
public class PersistenceView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JsonObject entity;
	
	/**
	 * 
	 */
	private List<JsonObject> entities = new ArrayList<>();

	/**
	 * 
	 */
	public void prePersist() {
		
	}
	
	/**
	 * 
	 */
	public void remove() {
		
	}

	public JsonObject getEntity() {
		return entity;
	}

	public void setEntity(final JsonObject entity) {
		this.entity = entity;
	}

	public List<JsonObject> getEntities() {
		return entities;
	}

	public void setEntities(final List<JsonObject> entities) {
		this.entities = entities;
	}
}
