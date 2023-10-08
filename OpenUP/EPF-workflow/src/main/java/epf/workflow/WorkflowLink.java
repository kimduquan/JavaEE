package epf.workflow;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
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
			return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}?version=" + version, workflow);
		}
		return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}", workflow);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link getWorkflowDefinitionLink(final String workflow, final String version) {
		if(version != null) {
			return LinkUtil.build(Naming.WORKFLOW, HttpMethod.GET, "{workflow}?version=" + version, workflow);
		}
		return LinkUtil.build(Naming.WORKFLOW, HttpMethod.GET, "{workflow}", workflow);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link transitionLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}/{state}?version=" + version, workflow, state);
		}
		return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}/{state}", workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link endLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}/{state}/end?version=" + version, workflow, state);
		}
		return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}/{state}/end", workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link compensateLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}/{state}/compensate?version=" + version, workflow, state);
		}
		return LinkUtil.build(Naming.WORKFLOW, HttpMethod.PUT, "{workflow}/{state}/compensate", workflow, state);
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
		return LinkUtil.build(Naming.SCHEDULE, HttpMethod.POST, "REST?service=" + service + ";method=" + method + ";recurringTimeInterval=" + recurringTimeInterval + path);
	}
}
