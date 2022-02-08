package epf.security.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.UsernamePasswordCredential;

import epf.naming.Naming;

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
	private final String ternant;

	/**
	 * @param name
	 * @param factory
	 * @param manager
	 */
	public JPAPrincipal(final String name, final String ternant, final EntityManagerFactory factory, final EntityManager manager) {
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
	
	/**
	 * @param credential
	 * @return
	 */
	public boolean equals(final UsernamePasswordCredential credential) {
		Objects.requireNonNull(credential, "UsernamePasswordCredential");
		final Map<String, Object> props = new HashMap<>();
        props.put(Naming.Persistence.JDBC.JDBC_USER, credential.getCaller());
        props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, credential.getPasswordAsString());
        return factory.getProperties().equals(props);
	}

	public String getTernant() {
		return ternant;
	}
	
	public EntityManagerFactory getFactory() {
		return factory;
	}
}
