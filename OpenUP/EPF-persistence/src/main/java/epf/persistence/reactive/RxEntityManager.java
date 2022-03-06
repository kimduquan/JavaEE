package epf.persistence.reactive;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.reactive.mutiny.Mutiny.Session;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.Query;
import epf.persistence.internal.util.EntityTypeUtil;
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
	private transient final Optional<Object> tenant;
	
	/**
	 * @param session
	 * @param factory
	 * @param tenant
	 */
	RxEntityManager(final SessionFactory factory, final Optional<Object> tenant) {
		this.tenant = tenant;
		this.factory = factory;
	}
	
	/**
	 * @param <T>
	 * @param metamodel
	 * @param session
	 * @param entityType
	 * @param id
	 * @return
	 */
	protected <T> Uni<T> find(final Metamodel metamodel, final Session session, final EntityType<T> entityType, final Object primaryKey) {
		final EntityGraph<T> entityGraph = createEntityGraph(metamodel, session, entityType);
		return session.find(entityGraph, primaryKey);
	}
	
	/**
	 * @param <T>
	 * @param metamodel
	 * @param session
	 * @param entityType
	 * @return
	 */
	protected <T> EntityGraph<T> createEntityGraph(final Metamodel metamodel, final Session session, final EntityType<T> entityType){
		final EntityGraph<T> entityGraph = session.createEntityGraph(entityType.getJavaType());
		for(Attribute<? super T, ?> attribute : entityType.getAttributes()) {
			if(attribute.getPersistentAttributeType().equals(PersistentAttributeType.EMBEDDED)) {
				final Subgraph<?> subgraph = entityGraph.addSubgraph(attribute.getName(), attribute.getJavaType());
				final EmbeddableType<?> embeddableType = metamodel.embeddable(attribute.getJavaType());
				for(Attribute<?, ?> embeddableAttribute : embeddableType.getAttributes()) {
					subgraph.addAttributeNodes(embeddableAttribute.getName());
				}
			}
			else {
				entityGraph.addAttributeNodes(attribute.getName());
			}
		}
		return entityGraph;
	}

	@Override
	public <T> CompletionStage<Void> persist(final T entity) {
		if(isJoinedToTransaction()) {
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(), 
						(session, transaction) -> session.persist(entity)
						)
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> session.persist(entity))
					.subscribeAsCompletionStage();
		}
		else {
			if(tenant.isPresent()) {
				return factory.withSession(tenant.get().toString(), session -> session.persist(entity)).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> session.persist(entity)).subscribeAsCompletionStage();
		}
	}

	@Override
	public <T> CompletionStage<T> merge(final T entity, final Function<T, T> function) {
		if(isJoinedToTransaction()) {
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(), 
						(session, transaction) -> session.merge(entity).map(function)
						)
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> session.merge(entity).map(function))
					.subscribeAsCompletionStage();
		}
		else {
			if(tenant.isPresent()) {
				return factory.withSession(tenant.get().toString(), session -> session.merge(entity).map(function)).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> session.merge(entity).map(function)).subscribeAsCompletionStage();
		}
	}

	@Override
	public <T> CompletionStage<Void> remove(final Class<T> cls, final Object primaryKey) {
		final EntityType<T> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), cls).get();
		if(isJoinedToTransaction()) {
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(), 
						(session, transaction) -> find(factory.getMetamodel(), session, entityType, primaryKey).chain(object -> session.remove(object))
						)
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction(
					(session, transaction) -> find(factory.getMetamodel(), session, entityType, primaryKey).chain(object -> session.remove(object))
					)
					.subscribeAsCompletionStage();
		}
		else {
			if(tenant.isPresent()) {
				return factory.withSession(
						tenant.get().toString(), 
						session -> find(factory.getMetamodel(), session, entityType, primaryKey).chain(object -> session.remove(object))
						).subscribeAsCompletionStage();
			}
			return factory.withSession(
					session -> find(factory.getMetamodel(), session, entityType, primaryKey).chain(object -> session.remove(object))
					).subscribeAsCompletionStage();
		}
	}

	@Override
	public <T, R> CompletionStage<R> find(final Class<T> entityClass, final Object primaryKey, final Function<T, R> function) {
		final EntityType<T> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), entityClass).get();
		if(isJoinedToTransaction()) {
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(),
						(session, transaction) -> {
							final Uni<T> entity = find(factory.getMetamodel(), session, entityType, primaryKey);
							return entity.map(function);
						})
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction(
					(session, transaction) -> {
						final Uni<T> entity = find(factory.getMetamodel(), session, entityType, primaryKey);
						return entity.map(function);
						}
					)
					.subscribeAsCompletionStage();
		}
		else {
			if(tenant.isPresent()) {
				return factory.withSession(tenant.get().toString(), session -> {
					final Uni<T> entity = find(factory.getMetamodel(), session, entityType, primaryKey);
					return entity.map(function);
				}).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> {
				final Uni<T> entity = find(factory.getMetamodel(), session, entityType, primaryKey);
				return entity.map(function);
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
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(), 
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
			if(tenant.isPresent()) {
				return factory.withSession(
						tenant.get().toString(), 
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
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(), 
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
			if(tenant.isPresent()) {
				return factory.withSession(
						tenant.get().toString(), 
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
