package epf.client;

import javax.ws.rs.core.Link;

/**
 * @author PC
 *
 */
public interface LinkUtil {

	static Link[] links(final Link... links) {
		final Link[] newLinks = new Link[links.length];
		for(int index = 0; index < links.length; index++) {
			newLinks[index] = Link.fromLink(links[index]).title("" + index).build();
		}
		return newLinks;
	}
}
