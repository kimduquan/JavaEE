package epf.function;

import javax.ws.rs.core.Link;

/**
 * @author PC
 *
 */
public interface LinkFunction {
	
	/**
	 * @param index
	 * @return
	 */
	Link toLink(final Integer index);
	
	/**
	 * @param funcs
	 * @return
	 */
	static Link[] toLinks(final LinkFunction... funcs) {
		int index = 0;
		final Link[] links = new Link[funcs.length];
		for(LinkFunction func : funcs) {
			links[index] = func.toLink(index);
			index++;
		}
		return links;
	}
}
