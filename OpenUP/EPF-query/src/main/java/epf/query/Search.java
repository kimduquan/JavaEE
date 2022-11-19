package epf.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import epf.naming.Naming;
import epf.query.client.EntityId;
import epf.query.internal.SchemaCache;

/**
 * 
 */
@ApplicationScoped
@Path(epf.naming.Naming.Query.SEARCH)
public class Search implements epf.query.client.Search {
	
	/**
	 *
	 */
	private static final String FULLTEXT_SEARCH = "SELECT * FROM FTL_SEARCH_DATA(?, ?, ?) WHERE SCHEMA LIKE ?;";
	
	/**
	 *
	 */
	private static final String FULLTEXT_SEARCH_COUNT = "SELECT COUNT(*) FROM FTL_SEARCH_DATA(?, ?, ?) WHERE SCHEMA LIKE ?;";
	
	/**
	 *
	 */
	@PersistenceContext(unitName = "EPF-query")
	private transient EntityManager manager;
	
	/**
	 *
	 */
	@Inject
	private transient SchemaCache schemaCache;

	@Override
	public Response search(
    		final String tenant,
    		final String text, 
    		final Integer firstResult, 
    		final Integer maxResults) {
		final Query query = createSearchQuery(FULLTEXT_SEARCH, tenant, text, maxResults != null ? maxResults : 0, firstResult != null ? firstResult : 0);
		final List<?> resultList = query.getResultList();
		final List<EntityId> entities = resultList.stream().map(this::toEntityId).filter(entityId -> entityId != null).collect(Collectors.toList());
		return Response.ok(entities).links(epf.query.client.Query.fetchEntitiesLink()).build();
	}

	@Override
	public Response count(
    		final String tenant,
    		final String text) {
		final Query query = createSearchQuery(FULLTEXT_SEARCH_COUNT, tenant, text, 0, 0);
		final Long count = (Long) query.getSingleResult();
		return Response.ok().header(epf.naming.Naming.Query.Client.ENTITY_COUNT, count).build();
	}
	
	/**
	 * @param obj
	 * @return
	 */
	private EntityId toEntityId(final Object obj) {
		final Object[] data = (Object[]) obj;
		final String table = String.valueOf(data[1]);
		final Object[] columns = (Object[]) data[2];
		final Object[] keys = (Object[]) data[3];
		final Optional<String> entityName = schemaCache.getEntityName(table);
		EntityId entityId = null;
		if(entityName.isPresent()) {
			final Optional<Class<?>> entityClass = schemaCache.getEntityClass(entityName.get());
			if(entityClass.isPresent()) {
				final Optional<String> entitySchema = schemaCache.getEntitySchema(entityClass.get());
				if(entitySchema.isPresent()) {
					entityId = new EntityId();
					entityId.setName(entityName.get());
					entityId.setSchema(entitySchema.get());

					final Map<String, Object> entityAttributes = new HashMap<>();
					final Map<String, String> entityFields = schemaCache.getColumnFields(entityName.get());
					int columnIndex = 0;
					for(Object column : columns) {
						final Object key = keys[columnIndex];
						final String columnName = String.valueOf(column);
						final String attributeName = entityFields.getOrDefault(columnName, columnName);
						entityAttributes.put(attributeName, key);
						columnIndex++;
					}
					entityId.setAttributes(entityAttributes);
				}
			}
		}
		return entityId;
	}
	
	/**
	 * @param query
	 * @param tenant
	 * @param text
	 * @param maxResults
	 * @param firstResult
	 * @return
	 */
	private Query createSearchQuery(final String query, final String tenant, final String text, final int maxResults, final int firstResult) {
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, "");
		final String filter = tenant == null ? "%" : "%_" + tenant;
		return manager.createNativeQuery(query)
				.setParameter(1, text)
				.setParameter(2, maxResults)
				.setParameter(3, firstResult)
				.setParameter(4, filter);
	}
}
