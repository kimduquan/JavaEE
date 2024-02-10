package epf.shell.event;

import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.EVENT)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface EventClient {

}
