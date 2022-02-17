package epf.persistence.reactive;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.Query;
import io.smallrye.mutiny.Uni;

/**
 * @author PC
 *
 */
public class RxEntityManager implements EntityManager {
	
	/**
	 * 
	 */
	private transient final SessionFactory factory;
	
	/**
	 * 
	 */
	private transient boolean joinedTransaction;
	
	/**
	 * 
	 */
	private transient final Optional<Object> ternant;
	
	/**
	 * @param session
	 * @param factory
	 * @param ternant
	 */
	RxEntityManager(final SessionFactory factory, final Optional<Object> ternant) {
		this.ternant = ternant;
		this.factory = factory;
	}

	@Override
	public <T> CompletionStage<Void> persist(final T entity) {
		if(isJoinedToTransaction()) {
			if(ternant.isPresent()) {
				return factory.withTransaction(
						ternant.get().toString(), 
						(session, transaction) -> session.persist(entity)
						)
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> session.persist(entity))
					.subscribeAsCompletionStage();
		}
		else {
			if(ternant.isPresent()) {
				return factory.withSession(ternant.get().toString(), session -> session.persist(entity)).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> session.persist(entity)).subscribeAsCompletionStage();
		}
	}

	@Override
	public <T> CompletionStage<T> merge(final T entity, final Function<T, T> function) {
		if(isJoinedToTransaction()) {
			if(ternant.isPresent()) {
				return factory.withTransaction(
						ternant.get().toString(), 
						(session, transaction) -> session.merge(entity).map(function)
						)
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> session.merge(entity).map(function))
					.subscribeAsCompletionStage();
		}
		else {
			if(ternant.isPresent()) {
				return factory.withSession(ternant.get().toString(), session -> session.merge(entity).map(function)).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> session.merge(entity).map(function)).subscribeAsCompletionStage();
		}
	}

	@Override
	public <T> CompletionStage<Void> remove(final T entity) {
		if(isJoinedToTransaction()) {
			if(ternant.isPresent()) {
				return factory.withTransaction(
						ternant.get().toString(), 
						(session, transaction) -> session.remove(entity)
						)
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> session.remove(entity))
					.subscribeAsCompletionStage();
		}
		else {
			if(ternant.isPresent()) {
				return factory.withSession(ternant.get().toString(), session -> session.remove(entity)).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> session.remove(entity)).subscribeAsCompletionStage();
		}
	}

	@Override
	public <T, R> CompletionStage<R> find(final Class<T> entityClass, final Object primaryKey, final Function<T, R> function) {
		if(isJoinedToTransaction()) {
			if(ternant.isPresent()) {
				return factory.withTransaction(
						ternant.get().toString(),
						(session, transaction) -> {
							//final String name = EntityGraphUtil.getEntityGraphName(entityClass);
							//final EntityGraph<T> entityGraph = session.getEntityGraph(entityClass, name);
							return session.find(entityClass, primaryKey).map(function);
						})
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction(
					(session, transaction) -> {
						//final String name = EntityGraphUtil.getEntityGraphName(entityClass);
						//final EntityGraph<T> entityGraph = session.getEntityGraph(entityClass, name);
						return session.find(entityClass, primaryKey).map(function);
						}
					)
					.subscribeAsCompletionStage();
		}
		else {
			if(ternant.isPresent()) {
				return factory.withSession(ternant.get().toString(), session -> {
					//final String name = EntityGraphUtil.getEntityGraphName(entityClass);
					//final EntityGraph<T> entityGraph = session.getEntityGraph(entityClass, name);
					return session.find(entityClass, primaryKey).map(function);
				}).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> {
				//final String name = EntityGraphUtil.getEntityGraphName(entityClass);
				//final EntityGraph<T> entityGraph = session.getEntityGraph(entityClass, name);
				return session.find(entityClass, primaryKey).map(function);
			}).subscribeAsCompletionStage();
		}
	}

	@Override
	public CompletionStage<Void> close() {
		return CompletableFuture.completedStage(null);
	}

	@Override
	public <R, T> CompletionStage<T> createQuery(final CriteriaQuery<R> criteriaQuery, final Function<Query<R>, CompletionStage<T>> function) {
		if(isJoinedToTransaction()) {
			if(ternant.isPresent()) {
				return factory.withTransaction(
						ternant.get().toString(), 
						(session, transaction) -> {
							final Query<R> query = new RxQuery<>(session.createQuery(criteriaQuery));
							return Uni.createFrom().completionStage(function.apply(query));
						})
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> {
				final Query<R> query = new RxQuery<>(session.createQuery(criteriaQuery));
				return Uni.createFrom().completionStage(function.apply(query));
			}).subscribeAsCompletionStage();
		}
		else {
			if(ternant.isPresent()) {
				return factory.withSession(
						ternant.get().toString(), 
						session -> {
							final Query<R> query = new RxQuery<>(session.createQuery(criteriaQuery));
							return Uni.createFrom().completionStage(function.apply(query));
							}
						)
				.subscribeAsCompletionStage();
			}
			return factory.withSession(
					session -> {
						final Query<R> query = new RxQuery<>(session.createQuery(criteriaQuery));
						return Uni.createFrom().completionStage(function.apply(query));
						}
					)
			.subscribeAsCompletionStage();
		}
	}

	@Override
	public <R, T> CompletionStage<T> createNativeQuery(final String sqlString, final Function<Query<R>, CompletionStage<T>> function) {
		if(isJoinedToTransaction()) {
			if(ternant.isPresent()) {
				return factory.withTransaction(
						ternant.get().toString(), 
						(session, transaction) -> {
							final Query<R> query = new RxQuery<>(session.createNativeQuery(sqlString));
							return Uni.createFrom().completionStage(function.apply(query));
						})
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> {
				final Query<R> query = new RxQuery<>(session.createNativeQuery(sqlString));
				return Uni.createFrom().completionStage(function.apply(query));
			}).subscribeAsCompletionStage();
		}
		else {
			if(ternant.isPresent()) {
				return factory.withSession(
						ternant.get().toString(), 
						session -> {
							final Query<R> query = new RxQuery<>(session.createNativeQuery(sqlString));
							return Uni.createFrom().completionStage(function.apply(query));
							}
						)
				.subscribeAsCompletionStage();
			}
			return factory.withSession(
					session -> {
						final Query<R> query = new RxQuery<>(session.createNativeQuery(sqlString));
						return Uni.createFrom().completionStage(function.apply(query));
						}
					)
			.subscribeAsCompletionStage();
		}
	}

	@Override
	public void joinTransaction() {
		joinedTransaction = true;
	}

	@Override
	public boolean isJoinedToTransaction() {
		return joinedTransaction;
	}
}
