package epf.security.client;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.SECURITY)
public interface Registration {

    /**
     * @param context
     * @return
     * @throws Exception
     */
    @Path(Naming.Security.PRINCIPAL)
    @POST
    Response createPrincipal(
    		@Context 
    		final SecurityContext context) throws Exception;
    
    /**
     * @param client
     * @return
     * @throws Exception
     */
    static Response createPrincipal(final Client client) throws Exception {
    	return client.request(
    			target -> target.path(Naming.Security.PRINCIPAL), 
    			req -> req
    			)
    			.post(null);
    }
}
