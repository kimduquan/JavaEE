package epf.webapp.workflow.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;

/**
 * 
 */
public interface Workflow {
	
	/**
	 * @param client
	 * @param workflow
	 * @param version
	 * @param input
	 * @return
	 */
	static Response start(final Client client, final String workflow, final String version, final Object input) {
		return client.request(
    			target -> target.path(workflow).queryParam("version", version), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(input));
	}
}
