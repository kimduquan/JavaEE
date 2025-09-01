package epf.event;

import org.eclipse.jnosql.mapping.graph.GraphTemplate;
import epf.event.schema.Link;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventCache {
	
	@Inject
	transient GraphTemplate graph;
	
	public void put(final String key, final Link link) {
		graph.insert(link);
	}
	
	public Link remove(final String key) {
		graph.delete(Link.class, key);
		return null;
	}
}
