package epf.gateway;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.auth.LoginConfig;
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@ApplicationPath("/")
@LoginConfig(authMethod = "MP-JWT", realmName = Naming.EPF)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Gateway extends Application {
	
}
