package epf.persistence.reactive;

import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.reactive.mutiny.Mutiny.Session;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import epf.persistence.util.EntityManager;
import epf.persistence.util.EntityManagerFactory;
import io.smallrye.mutiny.Uni;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class RxEntityManagerFactory implements EntityManagerFactory {
	
	/**
	 * 
	 */
	@Inject
	private transient SessionFactory sessionFactory;

	@Override
	public CompletionStage<EntityManager> createEntityManager() {
		final Uni<Session> session = sessionFactory.openSession();
		return session.subscribeAsCompletionStage().thenApply(ss -> new RxEntityManager(ss));
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return sessionFactory.getCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		return sessionFactory.getMetamodel();
	}

	@Override
	public void close() {
		sessionFactory.close();
	}
}
