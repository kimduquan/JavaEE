package epf.workflow;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;
import epf.naming.Naming;
import epf.workflow.util.LinkUtil;

/**
 * 
 */
public interface WorkflowLink {

	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link startLink(final String workflow, final String version) {
		if(version != null) {
			return LinkUtil.build(UriBuilder.fromUri("{workflow}").queryParam("version", version),  Naming.WORKFLOW, HttpMethod.PUT, workflow);
		}
		return LinkUtil.build(UriBuilder.fromUri("{workflow}"),  Naming.WORKFLOW, HttpMethod.PUT, workflow);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link getWorkflowDefinitionLink(final String workflow, final String version) {
		if(version != null) {
			return LinkUtil.build(UriBuilder.fromUri("{workflow}").queryParam("version", version), Naming.WORKFLOW, HttpMethod.GET, workflow);
		}
		return LinkUtil.build(UriBuilder.fromUri("{workflow}"), Naming.WORKFLOW, HttpMethod.GET, workflow);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link transitionLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return LinkUtil.build(UriBuilder.fromUri("{workflow}/{state}").queryParam("version", version), Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
		}
		return LinkUtil.build(UriBuilder.fromUri("{workflow}/{state}"), Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link endLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return LinkUtil.build(UriBuilder.fromUri("{workflow}/{state}/end").queryParam("version", version), Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
		}
		return LinkUtil.build(UriBuilder.fromUri("{workflow}/{state}/end"), Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link compensateLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return LinkUtil.build(UriBuilder.fromUri("{workflow}/{state}/compensate").queryParam("version", version), Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
		}
		return LinkUtil.build(UriBuilder.fromUri("{workflow}/{state}/compensate"), Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
	}
	
	/**
	 * @param service
	 * @param method
	 * @param path
	 * @param recurringTimeInterval
	 * @return
	 */
	static Link scheduleLink(
			final String service, 
			final String method,
			final String path,
			final String recurringTimeInterval) {
		return LinkUtil.build(UriBuilder.fromUri("REST").queryParam("service", service).queryParam("method", method).queryParam("recurringTimeInterval", recurringTimeInterval), Naming.SCHEDULE, HttpMethod.POST);
	}
}
