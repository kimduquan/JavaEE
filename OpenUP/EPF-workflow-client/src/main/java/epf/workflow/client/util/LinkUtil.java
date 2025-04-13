package epf.workflow.client.util;

import epf.naming.Naming;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;

public interface LinkUtil {

	static Link[] links(final Link... links) {
		final Link[] newLinks = new Link[links.length];
		for(int index = 0; index < links.length; index++) {
			newLinks[index] = Link.fromLink(links[index]).title("" + index).build();
		}
		return newLinks;
	}
	
	static Link build(final String service, final String method, final String path, final Object...values) {
		return Link.fromPath(path).type(method).rel(service).build(values);
	}
	
	static Link build(final UriBuilder builder, final int index, final String service, final String method, final Object...values) {
		return Link.fromUriBuilder(builder).title(String.valueOf(index)).type(method).rel(service).build(values);
	}
	
	static Link build(final UriBuilder builder, final String service, final String method, final Object...values) {
		return Link.fromUriBuilder(builder).type(method).rel(service).build(values);
	}
	
	static Link build(final UriBuilder builder, final int index, final boolean isFor, final boolean isSynchronized, final String service, final String method, final Object...values) {
		return Link.fromUriBuilder(builder).title(String.valueOf(index)).type(method).rel(service).param("for", isFor ? "true" : "").param("synchronized", String.valueOf(isSynchronized)).build(values);
	}
	
	static Link self() {
		return Link.fromPath("").rel(Naming.Client.Link.SELF).build();
	}
	
}
