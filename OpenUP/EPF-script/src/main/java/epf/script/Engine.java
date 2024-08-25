package epf.script;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jppf.jca.cci.JPPFConnection;
import epf.util.logging.LogManager;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionFactory;

/**
 * @author PC
 *
 */
@ApplicationScoped
@ApplicationPath("/")
public class Engine extends Application {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Application.class.getName());

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
	@PostConstruct
	public void postConstruct() {
		try {
			connection = (JPPFConnection) factory.getConnection();
		} 
		catch (ResourceException e) {
			LOGGER.throwing(factory.getClass().getName(), "getConnection", e);
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
			LOGGER.throwing(connection.getClass().getName(), "close", e);
		}
	}
	
	public JPPFConnection getConnection() {
		return connection;
	}
}
