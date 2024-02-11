package epf.event;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import epf.event.client.Event;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.QueryMapper.MapperDeleteWhere;
import jakarta.nosql.column.ColumnTemplate;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@ApplicationScoped
@Path(Naming.EVENT)
public class EventStore implements Event {
	
	/**
	 * 
	 */
	@Inject
	transient ColumnTemplate column;

	@RunOnVirtualThread
	@Override
	public Response consumes(final List<epf.event.schema.Event> events) throws Exception {
		final List<epf.event.schema.Event> insertedEvents = new ArrayList<>();
		column.insert(events).forEach(insertedEvents::add);
		return Response.ok(insertedEvents).build();
	}

	@RunOnVirtualThread
	@Override
	public Response produces(final List<epf.event.schema.Event> events) throws Exception {
		final List<epf.event.schema.Event> produceEvents = new ArrayList<>();
		for(epf.event.schema.Event event : events) {
			final List<epf.event.schema.Event> list = column.select(epf.event.schema.Event.class).where("source").eq(event.getSource()).and("type").eq(event.getType()).result();
			list.forEach(e -> {
				e.setData(event.getData());
				e.setDataContentType(event.getDataContentType());
				e.setTime(String.valueOf(Instant.now().toEpochMilli()));
			});
			column.update(list).forEach(produceEvents::add);
		}
		return Response.ok(produceEvents).build();
	}

	@RunOnVirtualThread
	@Override
	public Response remove(final List<String> ids) throws Exception {
		final Iterator<String> iterator = ids.iterator();
		MapperDeleteWhere delete = column.delete(epf.event.schema.Event.class).where("id").eq(iterator.next());
		while(iterator.hasNext()) {
			delete = delete.or("id").eq(iterator.next());
		}
		delete.execute();
		return Response.ok().build();
	}
}
