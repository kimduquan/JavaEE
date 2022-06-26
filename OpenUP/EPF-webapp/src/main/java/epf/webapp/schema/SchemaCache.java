package epf.webapp.schema;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.persistence.schema.client.Entity;
import epf.persistence.schema.client.Schema;
import epf.util.logging.LogManager;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.Session;

/**
 * 
 */
@ApplicationScoped
public class SchemaCache {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(SchemaCache.class.getName());

	/**
	 *
	 */
	private List<Entity> entities;
	
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
	protected void postConstruct() {
		try(Client client = gateway.newClient(epf.naming.Naming.SCHEMA)){
			client.authorization(session.getToken());
			try(Response response = Schema.getEntities(client)){
				entities = response.readEntity(new GenericType<List<Entity>>() {});
			}
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SchemaCache.entities]", e);
		}
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @return
	 */
	public Optional<Entity> getEntity(final String schema, final String entity) {
		return entities.stream().filter(e -> e.getTable().getSchema().equals(schema) && e.getName().equals(entity)).findFirst();
	}
}
