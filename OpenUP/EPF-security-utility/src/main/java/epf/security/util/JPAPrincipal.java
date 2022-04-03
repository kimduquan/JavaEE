package epf.security.util;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.security.enterprise.CallerPrincipal;

/**
 * @author PC
 *
 */
public class JPAPrincipal extends CallerPrincipal implements AutoCloseable {

	/**
	 * 
	 */
	private transient EntityManagerFactory factory;
	
	/**
	 * 
	 */
	private transient EntityManager defaultManager;
	
	/**
	 * 
	 */
	private transient final Optional<String> tenant;

	/**
	 * @param tenant
	 * @param name
	 * @param factory
	 * @param manager
	 */
	public JPAPrincipal(final Optional<String> tenant, final String name, final EntityManagerFactory factory, final EntityManager manager) {
		super(name);
		this.tenant = tenant;
		this.factory = factory;
		this.defaultManager = manager;
	}

	@Override
	public void close() {
		defaultManager.close();
		factory.close();
	}

	public Optional<String> getTenant() {
		return tenant;
	}
	
	public EntityManagerFactory getFactory() {
		return factory;
	}
	
	/**
	 * @param factory
	 * @param manager
	 */
	public void reset(final EntityManagerFactory factory, final EntityManager manager){
		defaultManager = manager;
		this.factory = factory;
	}
}
