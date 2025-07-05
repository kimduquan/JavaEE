package epf.event;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jnosql.communication.semistructured.CommunicationEntity;
import org.eclipse.jnosql.communication.semistructured.DatabaseManager;
import org.eclipse.jnosql.communication.semistructured.Element;
import org.eclipse.jnosql.communication.semistructured.Elements;
import org.eclipse.jnosql.communication.semistructured.SelectQuery;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.event.client.Event;
import epf.naming.Naming;
import epf.naming.Naming.Event.Schema;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Link.Builder;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path(Naming.EVENT)
public class EventStore implements Event {
	
	@Inject
	@Database(value = DatabaseType.COLUMN)
	transient DatabaseManager manager;
	
	@Inject
	transient EventCache eventCache;
	
	@Inject
	@Channel(Naming.Event.EPF_EVENT_OBSERVES)
	transient Emitter<Map<String, Object>> observes;
	
	private String eventId(final CommunicationEntity eventEntity) {
		return eventEntity.find(Schema.ID).get().get().toString();
	}
	
	private epf.event.schema.Link eventLink(final UriInfo uriInfo) {
		final Map<String, String> params = new HashMap<>();
		uriInfo.getQueryParameters().forEach((name, value) -> {
			if(value.isEmpty()) {
				params.put(name, "");
			}
			else {
				params.put(name, value.get(0));
			}
		});
		return epf.event.schema.Link.of(params);
	}
	
	private Map<String, Object> entityColumns(final CommunicationEntity entity) {
		final Map<String, Object> map = new HashMap<>();
		entity.elements().forEach(column -> {
			map.put(column.name(), column.get());
		});
		return map;
	}
	
	private SelectQuery.SelectWhere eventQuery(final epf.event.schema.Event event) {
		return SelectQuery.select().from("Event").where(Schema.SOURCE).eq(event.getSource()).and(Schema.TYPE).eq(event.getType()).and(Schema.TIME).eq("0");
	}
	
	private SelectQuery.SelectWhere eventQuery(final Map<String, Object> ext, SelectQuery.SelectWhere where) {
		for(Map.Entry<String, Object> param : ext.entrySet()) {
			where = where.and(param.getKey()).eq(param.getValue());
		}
		return where;
	}
	
	private Link eventLink(final epf.event.schema.Event event, final int index, final Map<String, Object> eventParams) throws Exception {
		final URI source = new URI(event.getSource());
		Builder builder = Event.link(source, eventParams).title("#" + index);
		return builder.build();
	}
	
	private CommunicationEntity eventEntity(final epf.event.schema.Event event, final List<Element> columns) {
		final CommunicationEntity eventEntity = CommunicationEntity.of("Event", columns);
		return eventEntity(eventEntity, event);
	}
	
	private CommunicationEntity eventEntity(final CommunicationEntity eventEntity, final epf.event.schema.Event event) {
		if(event.getData() != null) {
			eventEntity.add(Schema.DATA, event.getData());
		}
		if(event.getDataContentType() != null) {
			eventEntity.add(Schema.DATA_CONTENT_TYPE, event.getDataContentType());
		}
		if(event.getDataSchema() != null) {
			eventEntity.add(Schema.DATA_SCHEMA, event.getDataSchema());
		}
		if(event.getSource() != null) {
			eventEntity.add(Schema.SOURCE, event.getSource());
		}
		if(event.getSpecVersion() != null) {
			eventEntity.add(Schema.SPEC_VERSION, event.getSpecVersion());
		}
		if(event.getSubject() != null) {
			eventEntity.add(Schema.SUBJECT, event.getSubject());
		}
		if(event.getTime() != null) {
			eventEntity.add(Schema.TIME, event.getTime());
		}
		if(event.getType() != null) {
			eventEntity.add(Schema.TYPE, event.getType());
		}
		return eventEntity;
	}
	
	private void observes(final epf.event.schema.Event event, final Map<String, Object> ext, final List<Map<String, Object>> eventDatas, final List<Link> eventLinks) throws Exception {
		SelectQuery.SelectWhere where = eventQuery(event);
		where = eventQuery(ext, where);
		final SelectQuery query = null;
		final Stream<CommunicationEntity> entities = manager.select(query);
		event.setTime(String.valueOf(Instant.now().toEpochMilli()));
		entities.map(entity -> eventEntity(entity, event));
		final Iterable<CommunicationEntity> updatedEntities = manager.update(entities.collect(Collectors.toList()));
		int index = 0;
		for(CommunicationEntity eventEntity : updatedEntities) {
			final Map<String, Object> eventData = entityColumns(eventEntity);
			final String id = eventId(eventEntity);
			final Map<String, Object> eventParams = eventCache.remove(id).toMap();
			final Link eventLink = eventLink(event, index, eventParams);
			eventDatas.add(eventData);
			eventLinks.add(eventLink);
		}
	}

	@RunOnVirtualThread
	@Override
	public Response consumes(final UriInfo uriInfo, final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = epf.event.schema.Event.event(map, ext);
		final List<Element> columns = Elements.of(ext);
		final CommunicationEntity eventEntity = eventEntity(event, columns);
		final CommunicationEntity entity = manager.insert(eventEntity);
		final epf.event.schema.Link eventLink = eventLink(uriInfo);
		final String id = eventId(entity);
		eventLink.setId(id);
		eventCache.put(id, eventLink);
		return Response.ok(entityColumns(entity)).build();
	}

	@RunOnVirtualThread
	@Override
	public Response produces(final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = epf.event.schema.Event.event(map, ext);
		final List<Map<String, Object>> produceEvents = new ArrayList<>();
		final List<Link> produceEventLinks = new ArrayList<>();
		observes(event, ext, produceEvents, produceEventLinks);
		produceEvents.forEach(observes::send);
		return Response.status(Status.PARTIAL_CONTENT).entity(produceEvents).links(produceEventLinks.toArray(new Link[0])).build();
	}

	@RunOnVirtualThread
	@Override
	public Response observes(final List<Map<String, Object>> events) throws Exception {
		final List<Map<String, Object>> observes = new ArrayList<>();
		for(Map<String, Object> map : events) {
			final Map<String, Object> ext = new HashMap<>();
			final epf.event.schema.Event event = epf.event.schema.Event.event(map, ext);
			SelectQuery.SelectWhere where = eventQuery(event);
			where = eventQuery(ext, where);
			final SelectQuery query = where.build();
			final Stream<CommunicationEntity> entities = manager.select(query);
			entities.forEach(entity -> {
				final Map<String, Object> eventData = entityColumns(entity);
				observes.add(eventData);
			});
		}
		return Response.ok(observes).build();
	}
}
