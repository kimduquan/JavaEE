package epf.client.util;

import jakarta.ws.rs.core.Link;

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
}
