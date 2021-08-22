/**
 * 
 */
package epf.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.auth.LoginConfig;
import epf.schema.EPF;

/**
 * @author PC
 *
 */
@ApplicationScoped
@ApplicationPath("/")
@LoginConfig(authMethod = "MP-JWT", realmName = EPF.SCHEMA)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Service extends Application {
	
}
