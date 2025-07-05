package epf.webapp.workflow.client;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;

public interface Workflow {
	
	static Response start(final Client client, final String workflow, final String version, final Object input) {
		return client.request(
    			target -> target.path(workflow).queryParam("version", version), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(input));
	}
}
