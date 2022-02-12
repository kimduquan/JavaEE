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
	private transient final EntityManagerFactory factory;
	
	/**
	 * 
	 */
	private transient final EntityManager defaultManager;
	
	/**
	 * 
	 */
	private transient final Optional<String> ternant;

	/**
	 * @param ternant
	 * @param name
	 * @param factory
	 * @param manager
	 */
	public JPAPrincipal(final Optional<String> ternant, final String name, final EntityManagerFactory factory, final EntityManager manager) {
		super(name);
		this.ternant = ternant;
		this.factory = factory;
		this.defaultManager = manager;
	}
	
	public EntityManager getDefaultManager() {
		return defaultManager;
	}

	@Override
	public void close() {
		defaultManager.close();
		factory.close();
	}

	public Optional<String> getTernant() {
		return ternant;
	}
	
	public EntityManagerFactory getFactory() {
		return factory;
	}
}
