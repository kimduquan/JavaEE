package epf.persistence.internal.rx;

import java.util.Map;
import java.util.concurrent.CompletionStage;
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
public class RxEntityManagerFactory implements EntityManagerFactory {
	
	/**
	 * 
	 */
	private transient final javax.persistence.EntityManagerFactory factory;
	
	/**
	 * 
	 */
	private transient final SessionFactory sessionFactory;
	
	/**
	 * @param factory
	 */
	protected RxEntityManagerFactory(final javax.persistence.EntityManagerFactory factory){
		this.factory = factory;
		this.sessionFactory = factory.unwrap(SessionFactory.class);
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
	public boolean equals(Map<String, Object> props) {
		return factory.getProperties().equals(props);
	}
}
