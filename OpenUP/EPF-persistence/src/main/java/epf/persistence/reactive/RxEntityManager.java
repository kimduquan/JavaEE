package epf.persistence.reactive;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import javax.persistence.EntityGraph;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.mutiny.Mutiny.Session;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.Query;

/**
 * @author PC
 *
 */
public class RxEntityManager implements EntityManager {
	
	/**
	 * 
	 */
	private transient final Session session;
	
	/**
	 * @param session
	 */
	RxEntityManager(final Session session) {
		this.session = session;
	}

	@Override
	public <T> CompletionStage<Void> persist(final T entity) {
		return session.persist(entity).subscribeAsCompletionStage();
	}

	@Override
	public <T> CompletionStage<T> merge(final T entity) {
		return session.merge(entity).subscribeAsCompletionStage();
	}

	@Override
	public <T> CompletionStage<Void> remove(final T entity) {
		return session.remove(entity).subscribeAsCompletionStage();
	}

	@Override
	public <T> CompletionStage<T> find(final Class<T> entityClass, final Object primaryKey) {
		return session.find(entityClass, primaryKey).subscribeAsCompletionStage();
	}

	@Override
	public <T> CompletionStage<Void> refresh(final T entity) {
		return session.refresh(entity).subscribeAsCompletionStage();
	}

	@Override
	public CompletionStage<Void> close() {
		return session.close().subscribeAsCompletionStage();
	}

	@Override
	public <R> Query<R> createQuery(final CriteriaQuery<R> criteriaQuery) {
		final Mutiny.Query<R> query = session.createQuery(criteriaQuery);
		return new RxQuery<R>(query);
	}

	@Override
	public <R> Query<R> createNativeQuery(final String sqlString) {
		final Mutiny.Query<R> query = session.createNativeQuery(sqlString);
		return new RxQuery<>(query);
	}

	@Override
	public void detach(final Object entity) {
		session.detach(entity);
	}

	@Override
	public <T> CompletionStage<T> find(final Class<T> entityClass, final Object primaryKey, final Map<String, Object> properties) {
		@SuppressWarnings("unchecked")
		final EntityGraph<T> fetchGraph = (EntityGraph<T>) properties.get(FETCH_GRAPH);
		Objects.requireNonNull(fetchGraph, FETCH_GRAPH);
		return session.find(fetchGraph, primaryKey).subscribeAsCompletionStage();
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(final Class<T> rootType) {
		return session.createEntityGraph(rootType);
	}
}
