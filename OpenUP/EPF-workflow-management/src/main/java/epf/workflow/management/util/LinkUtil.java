package epf.workflow.management.util;

import epf.naming.Naming;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;

/**
 * @author PC
 *
 */
public interface LinkUtil {

	/**
	 * @param links
	 * @return
	 */
	static Link[] links(final Link... links) {
		final Link[] newLinks = new Link[links.length];
		for(int index = 0; index < links.length; index++) {
			newLinks[index] = Link.fromLink(links[index]).title("" + index).build();
		}
		return newLinks;
	}
	
	/**
	 * @param service
	 * @param method
	 * @param path
	 * @param values
	 * @return
	 */
	static Link build(final String service, final String method, final String path, final Object...values) {
		return Link.fromPath(path).type(method).rel(service).build(values);
	}
	/**
	 * @param builder
	 * @param service
	 * @param method
	 * @param values
	 * @return
	 */
	static Link build(final UriBuilder builder, final int index, final String service, final String method, final Object...values) {
		return Link.fromUriBuilder(builder).title(String.valueOf(index)).type(method).rel(service).build(values);
	}
	
	/**
	 * @return
	 */
	static Link self() {
		return Link.fromPath("").rel(Naming.Client.Link.SELF).build();
	}
	
}
