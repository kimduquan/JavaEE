package epf.persistence.reactive.internal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import epf.persistence.reactive.RxEntityManager;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class RxEntityManagerImpl implements RxEntityManager {
	
	/**
	 * 
	 */
	@Inject
	transient SessionFactory factory;

	@Override
	public Uni<Void> persist(final Object entity) {
		return factory.withTransaction(session -> session.persist(entity));
	}

	@Override
	public <T> Uni<T> merge(final T entity) {
		return factory.withSession(ss -> ss.merge(entity));
	}

	@Override
	public Uni<Void> remove(final Object entity) {
		return factory.withTransaction(session -> session.remove(entity));
	}

	@Override
	public <T> Uni<T> find(final Class<T> entityClass, final Object primaryKey) {
		return factory.withSession(session -> session.find(entityClass, primaryKey));
	}

	@Override
	public Uni<Void> flush() {
		return factory.withSession(session -> session.flush());
	}

	@Override
	public Uni<Void> refresh(final Object entity) {
		return factory.withSession(session -> session.refresh(entity));
	}

	@Override
	public Uni<Void> close() {
		return factory.withSession(session -> session.close());
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return factory.getCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		return factory.getMetamodel();
	}

}
