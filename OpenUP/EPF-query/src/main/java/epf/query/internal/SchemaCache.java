package epf.query.internal;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import epf.client.schema.EntityId;
import epf.schema.utility.SchemaUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class SchemaCache {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(SchemaCache.class.getName());

	/**
	 * 
	 */
	private transient SchemaUtil schemaUtil;
	
	/**
	 *
	 */
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	private transient EntityManager entityManager;
	
	/**
	 *
	 */
	private transient Map<String, EntityType<?>> entityClasses;
	
	/**
	 *
	 */
	private transient Map<String, EntityType<?>> entityNames;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final List<Class<?>> entityClasses = entityManager.getMetamodel().getEntities().stream().map(entity -> entity.getJavaType()).collect(Collectors.toList());
		schemaUtil = new SchemaUtil(entityClasses);
		this.entityClasses = new ConcurrentHashMap<>();
		entityNames = new ConcurrentHashMap<>();
		entityManager.getMetamodel().getEntities().forEach(entityType -> {
			this.entityClasses.put(entityType.getJavaType().getName(), entityType);
			entityNames.put(entityType.getName(), entityType);
		});
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		schemaUtil.clear();
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public Optional<EntityKey> getKey(final Object entity) {
		final Class<?> cls = entity.getClass();
		final Optional<String> entitySchema = schemaUtil.getEntitySchema(cls);
		final Optional<String> entityName = schemaUtil.getEntityName(cls);
		final Optional<Field> idField = schemaUtil.getEntityIdField(cls);
		Optional<Object> entityId = Optional.empty();
		if(entitySchema.isPresent() && entityName.isPresent() && idField.isPresent()) {
			try {
				entityId = Optional.ofNullable(idField.get().get(entity));
			} 
			catch (IllegalAccessException | IllegalArgumentException e) {
				LOGGER.log(Level.SEVERE, cls.getName() + "." + idField.get().getName(), e);
			}
		}
		Optional<EntityKey> key = Optional.empty();
		if(entitySchema.isPresent() && entityName.isPresent() && entityId.isPresent()) {
			key = Optional.of(new EntityKey(entitySchema.get(), entityName.get(), String.valueOf(entityId.get())));
		}
		return key;
	}
	
	/**
	 * @param entityCls
	 * @return
	 */
	public Optional<QueryKey> getQueryKey(final Class<?> entityCls) {
		final Optional<String> entitySchema = schemaUtil.getEntitySchema(entityCls);
		final Optional<String> entityName = schemaUtil.getEntityName(entityCls);
		if(entitySchema.isPresent() && entityName.isPresent()) {
			return Optional.of(new QueryKey(entitySchema.get(), entityName.get()));
		}
		return Optional.empty();
	}
	
	/**
	 * @param schema
	 * @param entityName
	 * @param entityId
	 * @return
	 */
	public EntityKey getKey(final String schema, final String entityName, final Object entityId) {
		return new EntityKey(schema, entityName, entityId);
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public EntityKey getSearchKey(final EntityId entityId) {
		return new EntityKey(entityId.getSchema(), entityId.getName(), entityId.getAttributes().values().iterator().next());
	}
	
	/**
	 * @param schema
	 * @param entityName
	 * @return
	 */
	public QueryKey getQueryKey(final String schema, final String entityName) {
		return new QueryKey(schema, entityName);
	}
	
	/**
	 * @param entity
	 * @return
	 * @throws Exception  
	 */
	public Optional<Object> getEntityId(final Object entity) throws Exception{
		final Class<?> cls = entity.getClass();
		final Optional<Field> idField = schemaUtil.getEntityIdField(cls);
		Optional<Object> entityId = Optional.empty();
		if(idField.isPresent()) {
			return Optional.ofNullable(idField.get().get(entity));
		}
		return entityId;
	}
	
	/**
	 * @param table
	 * @return
	 */
	public Optional<String> getEntityName(final String table){
		return schemaUtil.getEntityName(table);
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public Optional<String> getEntityClassName(final String entity) {
		final Optional<EntityType<?>> entityType = Optional.ofNullable(entityNames.get(entity));
		Optional<String> entityClassName = Optional.empty();
		if(entityType.isPresent()) {
			entityClassName = Optional.of(entityType.get().getJavaType().getName());
		}
		return entityClassName;
	}
}
