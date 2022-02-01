package epf.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.opentracing.Traced;
import epf.persistence.internal.Application;
import epf.persistence.internal.Session;
import epf.persistence.util.Embeddable;
import epf.persistence.util.Entity;
import epf.persistence.util.EntityManagerUtil;
import epf.persistence.util.EntityUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Traced
public class Request {
    
    /**
     * 
     */
    @Inject
    private transient Application application;
    

    
    /**
     * @return
     */
    public Session getSession(final SecurityContext context) {
    	final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    	return application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new);
    }
    
    /**
     * @param session
     * @param name
     * @return
     */
    public EntityType<?> getEntityType(final Session session, final String name){
    	return session.peekManager(
    			entityManager -> EntityUtil.findEntityType(entityManager.getMetamodel(), name)
    			)
    			.orElseThrow(NotFoundException::new);
    }
    
    /**
     * @param session
     * @param entityType
     * @param id
     * @return
     */
    public Object getEntity(final Session session, final EntityType<?> entityType, final Object id) {
    	return session
    			.peekManager(entityManager -> entityManager.find(entityType.getJavaType(), id))
    			.orElseThrow(NotFoundException::new);
    }
    
    /**
     * @param session
     * @param entityType
     * @param id
     * @return
     */
    @Transactional
    public Optional<Object> removeEntity(final Session session, final EntityType<?> entityType, final Object id) {
    	return session.peekManager(entityManager -> {
    		entityManager = EntityManagerUtil.joinTransaction(entityManager);
    		final Object entity = entityManager.find(entityType.getJavaType(), id);
    		if(entity != null) {
    			entityManager.remove(entity);
    			entityManager.flush();
    		}
			return entity;
    	});
    }
    
    /**
     * @param session
     * @param entity
     */
    @Transactional
    public void mergeEntity(final Session session, final Object entity) {
    	session.peekManager(entityManager -> {
    		entityManager = EntityManagerUtil.joinTransaction(entityManager);
        	final Object object = entityManager.merge(entity);
        	entityManager.flush();
        	return object;
        	}
    	);
    }
    

    
    /**
     * @param session
     * @param entity
     */
    @Transactional
    public Object persistEntity(final Session session, final Object entity) {
    	return session
    			.peekManager(entityManager -> {
    				entityManager = EntityManagerUtil.joinTransaction(entityManager);
		        	entityManager.persist(entity);
		        	entityManager.flush();
		        	return entity;
		        	}
    			)
    			.get();
    }
    
    /**
     * @param <T>
     * @param session
     * @return
     */
    public <T> Stream<Entity<T>> getEntities(final Session session) {
    	return session.peekManager(
    			entityManager -> {
    				final Stream<Entity<T>> stream = EntityUtil.getEntities(entityManager.getMetamodel());
    				return stream;
    				}
    			)
    			.get();
    }
    
    /**
     * @param <T>
     * @param session
     * @return
     */
    public <T> Stream<Embeddable<T>> getEmbeddables(final Session session){
    	return session.peekManager(entityManager -> {
    		final Stream<Embeddable<T>> stream = EntityUtil.getEmbeddables(entityManager.getMetamodel());
    		return stream;
    	})
    	.get();
    }
    
    /**
     * @param session
     * @param entityTables
     * @param entityAttributes
     */
    public void mapEntities(
    		final Session session,
    		final Map<String, EntityType<?>> entityTables, 
    		final Map<String, Map<String, Attribute<?,?>>> entityAttributes) {
    	session.peekManager(entityManager -> {
        	EntityUtil.mapEntities(entityManager.getMetamodel(), entityTables, entityAttributes);
        	return null;
    	});
    }
}
