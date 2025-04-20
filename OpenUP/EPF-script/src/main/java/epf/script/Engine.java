package epf.script;

import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jppf.jca.cci.JPPFConnection;
import epf.util.logging.LogManager;
import jakarta.resource.cci.ConnectionFactory;

@ApplicationScoped
@ApplicationPath("/")
public class Engine extends Application {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Application.class.getName());

	private transient JPPFConnection connection;
	
	@Resource(lookup = "eis/JPPFConnectionFactory")
	private transient ConnectionFactory factory;
	
	@PostConstruct
	public void postConstruct() {
		try {
			connection = (JPPFConnection) factory.getConnection();
		} 
		catch (jakarta.resource.ResourceException e) {
			LOGGER.throwing(factory.getClass().getName(), "getConnection", e);
		}
	}
	
	@PreDestroy
	public void preDestroy() {
		try {
			if(connection != null) {
				connection.close();
			}
		} 
		catch (javax.resource.ResourceException e) {
			LOGGER.throwing(connection.getClass().getName(), "close", e);
		}
	}
	
	public JPPFConnection getConnection() {
		return connection;
	}
}
