package epf.persistence.reactive;

import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import epf.naming.Naming;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.EntityManagerFactory;
import epf.util.MapUtil;

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
	transient SessionFactory sessionFactory;
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;

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

	@Override
	public EntityManager createEntityManager(final Map<String, Object> props) {
		final Optional<Object> tenant = MapUtil.get(props, Naming.Management.TENANT);
		return new RxEntityManager(sessionFactory, tenant);
	}
}
