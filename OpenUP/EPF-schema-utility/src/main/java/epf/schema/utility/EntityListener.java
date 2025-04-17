package epf.schema.utility;

import java.time.Instant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;

@ApplicationScoped
public class EntityListener {
	
	@Inject
	transient Event<epf.schema.utility.PostPersist> persistEvent;
	
	@Inject
	transient Event<epf.schema.utility.PostRemove> removeEvent;
	
	@Inject
	transient Event<epf.schema.utility.PostUpdate> updateEvent;
	
	@PostPersist
	protected void postPersist(final Object entity) {
		final epf.schema.utility.PostPersist event = new epf.schema.utility.PostPersist();
		event.setTime(Instant.now().toEpochMilli());
		if(entity instanceof EPFEntity) {
			EPFEntity epfEntity = (EPFEntity) entity;
			event.setTenant(epfEntity.getTenant());
			epfEntity.setTenant(null);
		}
		event.setEntity(entity);
		persistEvent.fire(event);
	}
	
	@PostRemove
	protected void postRemove(final Object entity) {
		final epf.schema.utility.PostRemove event = new epf.schema.utility.PostRemove();
		event.setTime(Instant.now().toEpochMilli());
		if(entity instanceof EPFEntity) {
			EPFEntity epfEntity = (EPFEntity) entity;
			event.setTenant(epfEntity.getTenant());
			epfEntity.setTenant(null);
		}
		event.setEntity(entity);
		removeEvent.fire(event);
	}
	
	@PostUpdate
	protected void postUpdate(final Object entity) {
		final epf.schema.utility.PostUpdate event = new epf.schema.utility.PostUpdate();
		event.setTime(Instant.now().toEpochMilli());
		if(entity instanceof EPFEntity) {
			EPFEntity epfEntity = (EPFEntity) entity;
			event.setTenant(epfEntity.getTenant());
			epfEntity.setTenant(null);
		}
		event.setEntity(entity);
		updateEvent.fire(event);
	}
}
