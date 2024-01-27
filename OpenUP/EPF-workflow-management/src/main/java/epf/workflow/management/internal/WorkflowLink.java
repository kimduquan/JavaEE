package epf.workflow.management.internal;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;
import java.util.Optional;
import epf.naming.Naming;
import epf.workflow.management.util.LinkUtil;

/**
 * 
 */
public interface WorkflowLink {
	
	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link getWorkflowLink(final int index, final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.Workflow.WORKFLOW_MANAGEMENT, HttpMethod.GET, workflow);
	}
}
