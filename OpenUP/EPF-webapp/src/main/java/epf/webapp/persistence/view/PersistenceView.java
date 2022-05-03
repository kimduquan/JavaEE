package epf.webapp.persistence.view;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;
import epf.client.cache.Cache;
import epf.client.util.Client;
import epf.persistence.schema.client.Attribute;
import epf.persistence.schema.client.Entity;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;
import epf.webapp.naming.Naming;
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
	private transient static final Logger LOGGER = LogManager.getLogger(PersistenceView.class.getName());
	
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
	private List<JsonObject> entities;
	
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
			try {
				entities = fetchEntities(currentEntity.get().getTable().getSchema(), currentEntity.get().getName());
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[PersistenceView.entities]", e);
			}
		}
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private List<JsonObject> fetchEntities(final String schema, final String entity) throws Exception{
		try(Client client = gateway.newClient(epf.naming.Naming.CACHE)){
			client.authorization(session.getToken());
			try(Response response = Cache.executeQuery(client, schema, t -> t.path(entity), null, null)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					try(JsonReader reader = Json.createReader(stream)){
						return reader
								.readArray()
								.stream()
								.map(value -> value.asJsonObject())
								.collect(Collectors.toList());
					}
				}
			}
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

	public List<JsonObject> getEntities() {
		return entities;
	}
}
