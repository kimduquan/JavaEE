package epf.webapp.persistence.view;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.persistence.schema.client.Attribute;
import epf.persistence.schema.client.Entity;
import epf.webapp.GatewayUtil;
import epf.webapp.naming.Naming;
import epf.webapp.persistence.internal.EntityCollector;
import epf.webapp.persistence.schema.SchemaCache;
import epf.webapp.security.Session;

/**
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
	private String schema;
	
	/**
	 *
	 */
	private String entity;
	
	/**
	 *
	 */
	private List<Attribute> attributes;
	
	/**
	 *
	 */
	private EntityCollector collector;
	
	/**
	 *
	 */
	@Inject
	private transient SchemaCache schemaCache;
	
	/**
	 *
	 */
	@Inject
	private transient GatewayUtil gateway;
	
	/**
	 *
	 */
	@Inject
	private transient Session session;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void initialize() {
		final Optional<Entity> currentEntity = schemaCache.getEntity(schema, entity);
		if(currentEntity.isPresent()) {
			attributes = currentEntity.get().getAttributes();
			collector = new EntityCollector(gateway, session.getToken(), schema, currentEntity.get());
		}
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(final String schema) {
		this.schema = schema;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(final String entity) {
		this.entity = entity;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @return
	 */
	public EntityCollector getCollector() {
		if(collector == null) {
			final Optional<Entity> currentEntity = schemaCache.getEntity(schema, entity);
			if(currentEntity.isPresent()) {
				attributes = currentEntity.get().getAttributes();
				collector = new EntityCollector(gateway, session.getToken(), schema, currentEntity.get());
			}
		}
		return collector;
	}
}
