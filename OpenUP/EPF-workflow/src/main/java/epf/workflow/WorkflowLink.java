package epf.workflow;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;

import java.time.Duration;
import java.util.Optional;

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
	static Link getWorkflowLink(final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.GET, workflow);
	}

	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link startLink(final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.POST, workflow);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link endLink(final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PUT, workflow);
	}
	
	static Link terminateLink(final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.DELETE, workflow);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link transitionLink(final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.POST, workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link compensateLink(final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PUT, workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link operationLink(final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.HEAD, workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link actionsLink(final String workflow, final Optional<String> version, final String state) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.PATCH, workflow, state);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @param action
	 * @param timeout
	 * @return
	 */
	static Link actionLink(final String workflow, final Optional<String> version, final String state, final String action, final Optional<Duration> timeout) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}/{state}/{action}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version);
		}
		if(timeout.isPresent()) {
			uri = uri.queryParam("timeout", timeout.get().toString());
		}
		return LinkUtil.build(uri, Naming.WORKFLOW, HttpMethod.POST, workflow, state, action);
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
