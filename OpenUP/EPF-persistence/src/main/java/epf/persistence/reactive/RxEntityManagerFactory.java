package epf.persistence.reactive;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.reactive.mutiny.Mutiny.Session;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import epf.naming.Naming;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.EntityManagerFactory;
import epf.persistence.internal.util.SchemaUtil;
import epf.util.MapUtil;
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
	transient SessionFactory sessionFactory;

	@Override
	public CompletionStage<EntityManager> createEntityManager(final Map<String, Object> props) {
		final Optional<Object> ternant = MapUtil.get(props, Naming.Management.TERNANT);
		Uni<Session> session;
		if(ternant.isPresent()) {
			final String schema = props.get(Naming.Persistence.Internal.SCHEMA).toString();
			final String ternantId = SchemaUtil.formatTernantId(schema, ternant.get().toString());
			session = sessionFactory.openSession(ternantId);
		}
		else {
			session = sessionFactory.openSession();
		}
		return session.map(ss -> {
					final EntityManager manager = new RxEntityManager(ss);
					return manager;
					}
				)
				.subscribeAsCompletionStage();
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
