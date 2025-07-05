package epf.webapp.persistence.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import epf.webapp.naming.Naming;

@ViewScoped
@Named(Naming.Persistence.VIEW)
public class PersistenceView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JsonObject entity;
	
	private List<JsonObject> entities = new ArrayList<>();

	public void prePersist() {
		
	}
	
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
