package epf.security.internal;

import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.security.enterprise.CallerPrincipal;

public class JPAPrincipal extends CallerPrincipal implements AutoCloseable {

	private transient final EntityManagerFactory factory;
	
	private transient final EntityManager manager;
	
	private transient final Optional<String> tenant;

	public JPAPrincipal(final Optional<String> tenant, final String name, final EntityManagerFactory factory, final EntityManager manager) {
		super(name);
		this.tenant = tenant;
		this.factory = factory;
		this.manager = manager;
	}

	@Override
	public void close() {
		manager.close();
		factory.close();
	}

	public Optional<String> getTenant() {
		return tenant;
	}

	public EntityManager getManager() {
		return manager;
	}
}
