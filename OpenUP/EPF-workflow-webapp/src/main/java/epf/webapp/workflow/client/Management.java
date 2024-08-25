package epf.webapp.workflow.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;

/**
 * 
 */
public interface Management {

	/**
	 * @param client
	 * @param entity
	 * @return
	 */
	static Response newWorkflowDefinition(final Client client, final Object entity) {
		return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(entity));
	}
	
	/**
	 * @param client
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Response getWorkflowDefinition(final Client client, final String workflow, final String version) {
		return client.request(
    			target -> target.path(workflow).queryParam("version", version), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
	}
}
