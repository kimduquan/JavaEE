package epf.workflow;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;
import java.util.Optional;
import epf.naming.Naming;
import epf.workflow.util.LinkUtil;

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
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.GET, workflow);
	}

	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link startLink(final int index, final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.POST, workflow);
	}
	
	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link endLink(final int index, final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PUT, workflow);
	}
	
	/**
	 * @param index
	 * @return
	 */
	static Link terminateLink(final int index) {
		UriBuilder uri = UriBuilder.fromUri("");
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.DELETE);
	}
	
	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link transitionLink(final int index, final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.POST, workflow, state);
	}
	
	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link compensateLink(final int index, final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
	}
	
	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @param state
	 * @param action
	 * @return
	 */
	static Link actionLink(final int index, final String workflow, final Optional<String> version, final String state, final String action) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/{action}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.POST, workflow, state, action);
	}
	
	/**
	 * @param index
	 * @param workflow
	 * @param version
	 * @param type
	 * @param state
	 * @return
	 */
	static Link stateLink(final int index, final String workflow, final Optional<String> version, final String type, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{type}");
		uri = uri.queryParam("state", state);
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PATCH, workflow, type);
	}
	
	/**
	 * @param index
	 * @param _synchronized
	 * @param workflow
	 * @param version
	 * @param state
	 * @param next
	 * @return
	 */
	static Link iterationLink(final int index, final boolean _synchronized, final String workflow, final Optional<String> version, final String state, final int next) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/iteration");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		uri = uri.queryParam("next", next);
		return LinkUtil.build(uri, index, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}
	
	/**
	 * @param index
	 * @param service
	 * @param method
	 * @param path
	 * @param recurringTimeInterval
	 * @return
	 */
	static Link scheduleLink(
			final int index,
			final String service, 
			final String method,
			final String path,
			final String recurringTimeInterval) {
		return LinkUtil.build(UriBuilder.fromUri("REST").queryParam("service", service).queryParam("method", method).queryParam("recurringTimeInterval", recurringTimeInterval), index, Naming.SCHEDULE, HttpMethod.POST);
	}
}
