package epf.query.search;

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
import epf.client.schema.EntityId;
import epf.naming.Naming;
import epf.query.internal.SchemaCache;

/**
 * 
 */
@ApplicationScoped
@Path(Naming.SEARCH)
public class Search implements epf.client.search.Search {
	
	/**
	 *
	 */
	private static final String FULLTEXT_SEARCH = "SELECT * FROM FTL_SEARCH_DATA(?, ?, ?);";
	
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
	public Response search(final String text, final Integer firstResult, final Integer maxResults) {
		final Query query = manager.createNativeQuery(FULLTEXT_SEARCH)
				.setParameter(1, text)
				.setParameter(2, maxResults != null ? maxResults : 0)
				.setParameter(3, firstResult != null ? firstResult : 0);
		final List<?> resultList = query.getResultList();
		final List<EntityId> entities = resultList.stream().map(this::toEntityId).filter(entityId -> entityId != null).collect(Collectors.toList());
		return Response.ok(entities).header(ENTITY_COUNT, resultList.size()).build();
	}

	@Override
	public Response count(final String text) {
		final Query query = manager.createNativeQuery(FULLTEXT_SEARCH)
				.setParameter(1, text)
				.setParameter(2, 0)
				.setParameter(3, 0);
		final List<?> resultList = query.getResultList();
		return Response.ok().header(ENTITY_COUNT, resultList.size()).build();
	}
	
	/**
	 * @param obj
	 * @return
	 */
	private EntityId toEntityId(final Object obj) {
		final Object[] data = (Object[]) obj;
		final String schema = String.valueOf(data[0]);
		final String table = String.valueOf(data[1]);
		final Object[] columns = (Object[]) data[2];
		final Object[] keys = (Object[]) data[3];
		final Optional<String> entityName = schemaCache.getEntityName(table);
		EntityId entityId = null;
		if(entityName.isPresent()) {
			entityId = new EntityId();
			entityId.setSchema(schema);
			entityId.setName(entityName.get());
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
		return entityId;
	}
}
