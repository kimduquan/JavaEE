package epf.persistence.reactive;

import java.util.concurrent.CompletionStage;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.mutiny.Mutiny.Session;
import epf.persistence.util.EntityManager;
import epf.persistence.util.Query;

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
	 * @param factory
	 */
	public RxEntityManager(final Session session) {
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

}
