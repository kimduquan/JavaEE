package epf.persistence.internal.rx;

import java.util.concurrent.CompletionStage;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.reactive.stage.Stage.Session;
import org.hibernate.reactive.stage.Stage.SessionFactory;
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
	private transient final CompletionStage<Session> session;
	
	/**
	 * 
	 */
	private transient final SessionFactory factory;
	
	/**
	 * @param session
	 * @param factory
	 */
	public RxEntityManager(final CompletionStage<Session> session, final SessionFactory factory) {
		this.session = session;
		this.factory = factory;
	}

	@Override
	public <T> CompletionStage<Void> persist(final T entity) {
		return session.thenCompose(session -> session.persist(entity));
	}

	@Override
	public <T> CompletionStage<T> merge(final T entity) {
		return session.thenCompose(session -> session.merge(entity));
	}

	@Override
	public <T> CompletionStage<Void> remove(final T entity) {
		return session.thenCompose(session -> session.remove(entity));
	}

	@Override
	public <T> CompletionStage<T> find(final Class<T> entityClass, final Object primaryKey) {
		return session.thenCompose(session -> session.find(entityClass, primaryKey));
	}

	@Override
	public <T> CompletionStage<Void> refresh(final T entity) {
		return session.thenCompose(session -> session.refresh(entity));
	}

	@Override
	public CompletionStage<Void> close() {
		return session.thenCompose(Session::close);
	}

	@Override
	public <R> Query<R> createQuery(final CriteriaQuery<R> criteriaQuery) {
		return new RxQuery<>(factory.openSession(), criteriaQuery);
	}

	@Override
	public <R> Query<R> createNativeQuery(final String sqlString) {
		return new RxQuery<>(factory.openSession(), sqlString);
	}

}
