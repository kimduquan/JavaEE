package epf.persistence;

import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.persistence.client.PersistenceInterface;
import epf.security.client.SecurityInterface;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Server implements HealthCheck {

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Server.class.getName());
	
	/**
	 * 
	 */
	private transient SecurityInterface security;
	
	/**
	 * 
	 */
	private transient PersistenceInterface persistence;
	
	/**
	 * 
	 */
	@PostConstruct
    protected void postConstruct() {
		try {
			persistence = (PersistenceInterface) UnicastRemoteObject.exportObject(new Remote(), 0);
			LocateRegistry.getRegistry().bind(Naming.Persistence.PERSISTENCE, persistence);
			LocateRegistry.getRegistry().bind(Naming.Persistence.PERSISTENCE_SECURITY, security);
		} 
    	catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
    protected void preDestroy() {
		try {
			LocateRegistry.getRegistry().unbind(Naming.Persistence.PERSISTENCE_SECURITY);
			LocateRegistry.getRegistry().unbind(Naming.Persistence.PERSISTENCE);
			UnicastRemoteObject.unexportObject(security, true);
			UnicastRemoteObject.unexportObject(persistence, true);
		} 
    	catch (Exception e) {
    		LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
		}
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence");
	}
}
