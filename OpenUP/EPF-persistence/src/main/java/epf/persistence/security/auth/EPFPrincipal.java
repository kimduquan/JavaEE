package epf.persistence.security.auth;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.UsernamePasswordCredential;

/**
 * @author PC
 *
 */
public class EPFPrincipal extends CallerPrincipal implements Closeable {
	
	/**
	 * 
	 */
	private transient final EntityManagerFactory factory;
	/**
	 * 
	 */
	private transient final EntityManager manager;

	/**
	 * @param name
	 */
	public EPFPrincipal(final String name, final EntityManager manager, final EntityManagerFactory factory) {
		super(name);
		this.factory = factory;
		this.manager = manager;
	}

	public EntityManager getManager() {
		return manager;
	}

	@Override
	public void close() throws IOException {
		if(manager.isOpen()) {
			manager.close();
		}
		if(factory.isOpen()) {
			factory.close();
		}
	}
	
	/**
	 * @param credential
	 * @return
	 */
	public boolean equals(final UsernamePasswordCredential credential) {
		Objects.requireNonNull(credential, "UsernamePasswordCredential");
		final Map<String, Object> props = new HashMap<>();
        props.put(IdentityStore.JDBC_USER, credential.getCaller());
        props.put(IdentityStore.JDBC_PASSWORD, credential.getPasswordAsString());
        return props.equals(factory.getProperties());
	}
	
	/**
	 * @return
	 */
	public EntityManager newManager() {
		return factory.createEntityManager();
	}
}
