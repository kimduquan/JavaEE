package epf.persistence.reactive;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import org.hibernate.reactive.mutiny.Mutiny.Session;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.Query;
import epf.persistence.internal.util.EntityTypeUtil;
import epf.util.logging.LogManager;
import io.smallrye.mutiny.Uni;

/**
 * @author PC
 *
 */
public class RxEntityManager implements EntityManager {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(RxEntityManager.class.getName());
	
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
	 * @param entityType
	 * @param session
	 * @param entity
	 * @return
	 */
	protected <T> Uni<T> fetchAttributes(final Metamodel metamodel, final Session session, final EntityType<?> entityType, final Uni<T> entity) {
		Uni<T> result = entity;
		for(PluralAttribute<?, ?, ?> attr : entityType.getPluralAttributes()) {
			@SuppressWarnings("unchecked")
			final Attribute<T, ?> attribute = (Attribute<T, ?>) attr;
			result = result.chain(entityObject -> session.fetch(entityObject, attribute).map(r -> entityObject));
		}
		final List<EmbeddableType<?>> embeddedAttributes = 
		entityType.getSingularAttributes()
		.stream()
		.filter(attr -> attr.getPersistentAttributeType() == PersistentAttributeType.EMBEDDED)
		.map(attr -> metamodel.embeddable(attr.getJavaType()))
		.collect(Collectors.toList());
		for(EmbeddableType<?> embeddable : embeddedAttributes) {
			for(PluralAttribute<?, ?, ?> attribute : embeddable.getPluralAttributes()) {
				final Field field = (Field) attribute.getJavaMember();
				field.setAccessible(true);
				@SuppressWarnings("unchecked")
				final Attribute<Object, ?> embeddedAttribute = (Attribute<Object, ?>) attribute;
				result = result.chain(entityObject -> {
					try {
						field.setAccessible(true);
						final Object object = field.get(entityObject);
						if(object != null) {
							return session.fetch(object, embeddedAttribute).map(r -> entityObject);
						}
					} 
					catch (Exception e) {
						LOGGER.log(Level.SEVERE, "fetchAttributes", e);
					}
					return Uni.createFrom().item(entityObject);
				});
			}
		}
		return result;
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
	public <T> CompletionStage<Void> remove(final T entity) {
		if(isJoinedToTransaction()) {
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(), 
						(session, transaction) -> session.remove(entity)
						)
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction((session, transaction) -> session.remove(entity))
					.subscribeAsCompletionStage();
		}
		else {
			if(tenant.isPresent()) {
				return factory.withSession(tenant.get().toString(), session -> session.remove(entity)).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> session.remove(entity)).subscribeAsCompletionStage();
		}
	}

	@Override
	public <T, R> CompletionStage<R> find(final Class<T> entityClass, final Object primaryKey, final Function<T, R> function) {
		final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), entityClass).get();
		if(isJoinedToTransaction()) {
			if(tenant.isPresent()) {
				return factory.withTransaction(
						tenant.get().toString(),
						(session, transaction) -> {
							final Uni<T> entity = session.find(entityClass, primaryKey);
							return fetchAttributes(factory.getMetamodel(), session, entityType, entity).map(function);
						})
						.subscribeAsCompletionStage();
			}
			return factory.withTransaction(
					(session, transaction) -> {
						final Uni<T> entity = session.find(entityClass, primaryKey);
						return fetchAttributes(factory.getMetamodel(), session, entityType, entity).map(function);
						}
					)
					.subscribeAsCompletionStage();
		}
		else {
			if(tenant.isPresent()) {
				return factory.withSession(tenant.get().toString(), session -> {
					final Uni<T> entity = session.find(entityClass, primaryKey);
					return fetchAttributes(factory.getMetamodel(), session, entityType, entity).map(function);
				}).subscribeAsCompletionStage();
			}
			return factory.withSession(session -> {
				final Uni<T> entity = session.find(entityClass, primaryKey);
				return fetchAttributes(factory.getMetamodel(), session, entityType, entity).map(function);
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
