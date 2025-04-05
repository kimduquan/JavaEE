package epf.event;

import java.util.Optional;
import org.eclipse.jnosql.communication.keyvalue.KeyValueEntity;
import epf.event.schema.Link;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.keyvalue.KeyValueTemplate;

@ApplicationScoped
public class EventCache {
	
	@Inject
	transient KeyValueTemplate cache;
	
	public void put(final String key, final Link link) {
		cache.put(KeyValueEntity.of(key, link));
	}
	
	public Link remove(final String key) {
		final Optional<Link> value = cache.get(key, Link.class);
		cache.delete(key);
		return value.orElse(null);
	}
}
