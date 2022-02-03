package epf.persistence.security.auth;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import epf.persistence.util.EntityManager;
import epf.persistence.util.EntityManagerFactory;

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
	private transient final EntityManager defaultManager;

	/**
	 * @param name
	 * @param factory
	 * @param manager
	 */
	public EPFPrincipal(final String name, final EntityManagerFactory factory, final EntityManager manager) {
		super(name);
		this.factory = factory;
		this.defaultManager = manager;
	}
	
	public EntityManager getDefaultManager() {
		return defaultManager;
	}

	@Override
	public void close() throws IOException {
		defaultManager.close();
		factory.close();
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
        return factory.equals(props);
	}
	
	public EntityManagerFactory getFactory() {
		return factory;
	}
}
