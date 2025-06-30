package epf.workflow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jnosql.communication.semistructured.CommunicationEntity;
import org.eclipse.jnosql.communication.semistructured.CriteriaCondition;
import org.eclipse.jnosql.communication.semistructured.DatabaseManager;
import org.eclipse.jnosql.communication.semistructured.SelectQuery;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;
import epf.naming.Naming;
import epf.workflow.schema.Correlation;
import epf.workflow.schema.EventFilter;
import epf.workflow.schema.EventProperties;
import epf.workflow.spi.EventFilterService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventFilterServiceImpl implements EventFilterService {
	
	@Inject
	@Database(value = DatabaseType.COLUMN)
	transient DatabaseManager database;

	@Override
	public List<EventFilter> getEventFilters(final EventProperties event) throws Error {
		final CriteriaCondition eventFilterCondition = buildEventFilterCondition(event);
		final SelectQuery eventFilterQuery = buildEventFilterQuery(eventFilterCondition);
		final List<EventFilter> eventFilters = queryEventFilters(eventFilterQuery);
		return eventFilters;
	}
	
	private CriteriaCondition buildEventFilterCondition(final EventProperties event) {
		final CriteriaCondition sourceFilter = CriteriaCondition.or(CriteriaCondition.eq(Naming.Event.Schema.SOURCE, null), CriteriaCondition.eq(Naming.Event.Schema.SOURCE, event.getSource()));
		final CriteriaCondition typeFilter = CriteriaCondition.or(CriteriaCondition.eq(Naming.Event.Schema.TYPE, null), CriteriaCondition.eq(Naming.Event.Schema.TYPE, event.getType()));
		CriteriaCondition datacontenttypeFilter;
		if(event.getDatacontenttype() != null) {
			datacontenttypeFilter = CriteriaCondition.or(CriteriaCondition.eq(Naming.Event.Schema.DATA_CONTENT_TYPE, null), CriteriaCondition.eq(Naming.Event.Schema.DATA_CONTENT_TYPE, event.getDatacontenttype()));
		}
		else {
			datacontenttypeFilter = CriteriaCondition.eq(Naming.Event.Schema.DATA_CONTENT_TYPE, null);
		}
		CriteriaCondition dataschemaFilter;
		if(event.getDataschema() != null) {
			dataschemaFilter = CriteriaCondition.or(CriteriaCondition.eq(Naming.Event.Schema.DATA_SCHEMA, null), CriteriaCondition.eq(Naming.Event.Schema.DATA_SCHEMA, event.getDataschema()));
		}
		else {
			dataschemaFilter = CriteriaCondition.eq(Naming.Event.Schema.DATA_SCHEMA, null);
		}
		CriteriaCondition subjectFilter;
		if(event.getSubject() != null) {
			subjectFilter = CriteriaCondition.or(CriteriaCondition.eq(Naming.Event.Schema.SUBJECT, null), CriteriaCondition.eq(Naming.Event.Schema.SUBJECT, event.getSubject()));
		}
		else {
			subjectFilter = CriteriaCondition.eq(Naming.Event.Schema.SUBJECT, null);
		}
		return CriteriaCondition.and(sourceFilter, typeFilter, datacontenttypeFilter, dataschemaFilter, subjectFilter);
	}
	
	private SelectQuery buildEventFilterQuery(final CriteriaCondition eventFilterCondition) {
		return SelectQuery.builder().from(Naming.Workflow.EVENT_FILTER).where(eventFilterCondition).build();
	}
	
	private EventFilter buildEventFilter(final CommunicationEntity entity) {
		final EventFilter eventFilter = new EventFilter();
		eventFilter.setWith(new EventProperties());
		final Map<String, Object> extData = new LinkedHashMap<>();
		final Map<String, Object> data = entity.toMap();
		data.forEach((property, value) -> {
			if(Naming.Event.Schema.ID.equals(property)) {
				eventFilter.getWith().setId((String)value);
			}
			else if(Naming.Event.Schema.SOURCE.equals(property)) {
				eventFilter.getWith().setSource((String)value);
			}
			else if(Naming.Event.Schema.TYPE.equals(property)) {
				eventFilter.getWith().setType((String)value);
			}
			else if(Naming.Event.Schema.DATA_CONTENT_TYPE.equals(property)) {
				eventFilter.getWith().setDatacontenttype(value != null ? (String)value : null);
			}
			else if(Naming.Event.Schema.DATA_SCHEMA.equals(property)) {
				eventFilter.getWith().setDataschema(value != null ? (String)value : null);
			}
			else if(Naming.Event.Schema.SUBJECT.equals(property)) {
				eventFilter.getWith().setSubject(value != null ? (String)value : null);
			}
			else if(Naming.Event.Schema.TIME.equals(property)) {
				eventFilter.getWith().setTime(value != null ? (String)value : null);
			}
			else if(!Naming.Event.Schema.SPEC_VERSION.equals(property)) {
				extData.put(property, value);
			}
		});
		if(!extData.isEmpty()) {
			final Map<String, Correlation> correlate = new HashMap<>();
			extData.forEach((property, value) -> {
				final Correlation c = new Correlation();
				c.setFrom(value != null ? (String)value : null);
				correlate.put(property, c);
			});
			eventFilter.setCorrelate(correlate);
		}
		return eventFilter;
	}
	
	private List<EventFilter> queryEventFilters(final SelectQuery eventFilterQuery) {
		final List<EventFilter> eventFilters = new ArrayList<>();
		database.select(eventFilterQuery).forEach(entity -> {
			final EventFilter eventFilter = buildEventFilter(entity);
			eventFilters.add(eventFilter);
		});
		return eventFilters;
	}
}
