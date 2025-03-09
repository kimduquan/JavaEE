package epf.gateway;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
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
