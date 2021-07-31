/**
 * 
 */
package openup.client;

import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * @author PC
 *
 */
@RegisterRestClient(configKey = "epf.gateway")
@Path("security")
public interface SecurityUtil extends AutoCloseable {

}
