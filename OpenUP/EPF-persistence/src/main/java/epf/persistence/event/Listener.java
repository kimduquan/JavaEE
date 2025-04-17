package epf.persistence.event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.schema.utility.Request;

@ApplicationScoped
public class Listener {
	
	@Inject
	transient Event<AsyncEvent> emitter;
	
	@Inject
    transient Request request;
	
	public void postPersist(@Observes final PostPersist event) throws Exception {
		event.setSchema(request.getSchema());
		event.setTenant(request.getTenant());
		emitter.fireAsync(new AsyncEvent(event));
	}
	
	public void postRemove(@Observes final PostRemove event) throws Exception {
		event.setSchema(request.getSchema());
		event.setTenant(request.getTenant());
		emitter.fireAsync(new AsyncEvent(event));
	}
	
	public void postUpdate(@Observes final PostUpdate event) throws Exception {
		event.setSchema(request.getSchema());
		event.setTenant(request.getTenant());
		emitter.fireAsync(new AsyncEvent(event));
	}
}
