package epf.function;

import jakarta.ws.rs.core.Link;

public interface LinkFunction {
	
	Link toLink(final Integer index);
	
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
