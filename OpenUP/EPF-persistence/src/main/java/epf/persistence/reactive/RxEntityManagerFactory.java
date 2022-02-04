package epf.persistence.reactive;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.reactive.stage.Stage.Session;
import org.hibernate.reactive.stage.Stage.SessionFactory;
import epf.persistence.util.EntityManager;
import epf.persistence.util.EntityManagerFactory;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class RxEntityManagerFactory implements EntityManagerFactory {
	
	/**
	 * 
	 */
	private transient SessionFactory sessionFactory;
	
	/**
	 * 
	 */
	@Inject
	transient javax.persistence.EntityManagerFactory factory;
	
	/**
	 * 
	 */
	@PostConstruct
	void postConstruct() {
		sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public CompletionStage<EntityManager> createEntityManager() {
		final CompletionStage<Session> session = sessionFactory.openSession();
		return session.thenApply(ss -> new RxEntityManager(session, sessionFactory));
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return factory.getCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		return factory.getMetamodel();
	}

	@Override
	public void close() {
		sessionFactory.close();
		factory.close();
	}

	@Override
	public boolean equals(final Map<String, Object> props) {
		return factory.getProperties().equals(props);
	}
}
