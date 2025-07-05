package epf.webapp.workflow.client;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;

public interface Management {

	static Response newWorkflowDefinition(final Client client, final Object entity) {
		return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(entity));
	}
	
	static Response getWorkflowDefinition(final Client client, final String workflow, final String version) {
		return client.request(
    			target -> target.path(workflow).queryParam("version", version), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
	}
}
