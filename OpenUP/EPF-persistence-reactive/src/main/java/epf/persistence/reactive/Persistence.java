package epf.persistence.reactive;

import java.io.InputStream;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.metamodel.EntityType;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import epf.naming.Naming;
import epf.persistence.util.EntityUtil;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path(Naming.PERSISTENCE)
@ApplicationScoped
public class Persistence implements epf.persistence.client.RxPersistence {
	
	/**
	 * 
	 */
	@Inject
	transient RxEntityManager entityManager;
	
	/**
	 * 
	 */
	@Inject
	transient Validator validator;
    
    /**
     * @param entityType
     * @param id
     * @return
     */
    protected <T> Uni<T> findEntity(final EntityType<T> entityType, final Object id) {
    	return entityManager.find(entityType.getJavaType(), id)
    			.onItem()
    			.ifNull()
    			.failWith(NotFoundException::new);
    }

	@Override
	public Uni<Object> persist(final String schema, final String name, final InputStream body)
			throws Exception {
		final EntityType<?> entityType = EntityUtil.findEntityType(entityManager.getMetamodel(), name);
		final Object entity = EntityUtil.toObject(entityType, body);
		validator.validate(entity);
		return entityManager.persist(entity).replaceWith(entity);
	}

	@Override
	public Uni<Void> merge(final String schema, final String name, final String id, final InputStream body)
			throws Exception {
		final EntityType<?> entityType = EntityUtil.findEntityType(entityManager.getMetamodel(), name);
		final Object entityId = EntityUtil.getEntityId(entityType, id);
		final Uni<Object> entity = findEntity(entityType, entityId)
				.map(e -> EntityUtil.toObject(entityType, body));
		return entity.invoke(validator::validate)
				.invoke(entityManager::merge)
				.replaceWithVoid();
	}

	@Override
	public Uni<Void> remove(final String schema, final String name, final String id) {
		final EntityType<?> entityType = EntityUtil.findEntityType(entityManager.getMetamodel(), name);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	return findEntity(entityType, entityId)
    			.invoke(entityManager::remove)
    			.replaceWithVoid();
	}

	@Override
	public Uni<Object> find(final String schema, final String name, final String id) {
		final EntityType<?> entityType = EntityUtil.findEntityType(entityManager.getMetamodel(), name);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	return findEntity(entityType, entityId).map(entity -> entity);
	}

	@Override
	public Multi<Object> executeQuery(final String schema, final List<PathSegment> paths, final Integer firstResult, final Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

}
