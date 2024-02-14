package epf.event;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jnosql.communication.column.Column;
import org.eclipse.jnosql.communication.column.ColumnEntity;
import org.eclipse.jnosql.communication.column.ColumnManager;
import org.eclipse.jnosql.communication.column.ColumnQuery;
import org.eclipse.jnosql.communication.column.ColumnQuery.ColumnWhere;
import org.eclipse.jnosql.communication.column.Columns;
import epf.event.client.Event;
import epf.naming.Naming;
import epf.naming.Naming.Event.Schema;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.column.ColumnTemplate;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

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
	
	/**
	 * 
	 */
	@Inject
	transient ColumnManager manager;
	
	private Map<String, Object> entityColumns(final ColumnEntity entity) {
		final Map<String, Object> map = new HashMap<>();
		entity.columns().forEach(column -> {
			map.put(column.name(), column.get());
		});
		return map;
	}
	
	private ColumnWhere eventQuery(final epf.event.schema.Event event) {
		return ColumnQuery.select().from("Event").where(Schema.SOURCE).eq(event.getSource()).and(Schema.TYPE).eq(event.getType()).and(Schema.TIME).eq("0");
	}
	
	private ColumnWhere eventQuery(final Map<String, Object> ext, ColumnWhere where) {
		for(Map.Entry<String, Object> param : ext.entrySet()) {
			where = where.and(param.getKey()).eq(param.getValue());
		}
		return where;
	}
	
	private Link eventLink(final ColumnEntity eventEntity, final int index) throws Exception {
		final URI source = new URI(eventEntity.find(Schema.SOURCE).get().get().toString());
		final String rel = source.getHost();
		final URI uri = new URI(source.getRawPath() + source.getRawQuery());
		final String type = eventEntity.find(Schema.TYPE).get().get().toString();
		final Link eventLink = Link.fromUri(uri).type(type).rel(rel).title("#" + index).build();
		return eventLink;
	}
	
	private ColumnEntity eventEntity(final epf.event.schema.Event event, final List<Column> columns) {
		final ColumnEntity eventEntity = ColumnEntity.of("Event", columns);
		return eventEntity(eventEntity, event);
	}
	
	private ColumnEntity eventEntity(final ColumnEntity eventEntity, final epf.event.schema.Event event) {
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
		ColumnWhere where = eventQuery(event);
		where = eventQuery(ext, where);
		final ColumnQuery query = where.build();
		final Stream<ColumnEntity> entities = manager.select(query);
		event.setTime(String.valueOf(Instant.now().toEpochMilli()));
		entities.map(entity -> eventEntity(entity, event));
		final Iterable<ColumnEntity> updatedEntities = manager.update(entities.collect(Collectors.toList()));
		int index = 0;
		for(ColumnEntity eventEntity : updatedEntities) {
			final Map<String, Object> eventData = entityColumns(eventEntity);
			final Link eventLink = eventLink(eventEntity, index);
			eventDatas.add(eventData);
			eventLinks.add(eventLink);
		}
	}

	@RunOnVirtualThread
	@Override
	public Response consumes(final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = epf.event.schema.Event.event(map, ext);
		final List<Column> columns = Columns.of(ext);
		final ColumnEntity eventEntity = eventEntity(event, columns);
		final ColumnEntity entity = manager.insert(eventEntity);
		return Response.ok(entityColumns(entity)).build();
	}

	@RunOnVirtualThread
	@Override
	public Response produces(final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = epf.event.schema.Event.event(map, ext);
		final List<Map<String, Object>> eventDatas = new ArrayList<>();
		final List<Link> eventLinks = new ArrayList<>();
		observes(event, ext, eventDatas, eventLinks);
		return Response.status(Status.PARTIAL_CONTENT).entity(eventDatas).links(eventLinks.toArray(new Link[0])).build();
	}
}
