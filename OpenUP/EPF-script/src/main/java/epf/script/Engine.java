package epf.script;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jppf.jca.cci.JPPFConnection;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionFactory;

/**
 * @author PC
 *
 */
@ApplicationScoped
@ApplicationPath("/script")
public class Engine extends Application {

	/**
	 * 
	 */
	private transient JPPFConnection connection;
	
	/**
	 * 
	 */
	@Resource(lookup = "eis/JPPFConnectionFactory")
	private transient ConnectionFactory factory;
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	@PostConstruct
	public void postConstruct() {
		try {
			connection = (JPPFConnection) factory.getConnection();
		} 
		catch (ResourceException e) {
			logger.throwing(factory.getClass().getName(), "getConnection", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	public void preDestroy() {
		try {
			if(connection != null) {
				connection.close();
			}
		} 
		catch (ResourceException e) {
			logger.throwing(connection.getClass().getName(), "close", e);
		}
	}
	
	public JPPFConnection getConnection() {
		return connection;
	}
}
