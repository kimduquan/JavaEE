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
		final CriteriaCondition idFilter = CriteriaCondition.or(CriteriaCondition.eq("id", null), CriteriaCondition.eq("id", event.getId()));
		final CriteriaCondition sourceFilter = CriteriaCondition.or(CriteriaCondition.eq("source", null), CriteriaCondition.eq("source", event.getSource()));
		final CriteriaCondition typeFilter = CriteriaCondition.or(CriteriaCondition.eq("type", null), CriteriaCondition.eq("type", event.getType()));
		CriteriaCondition datacontenttypeFilter;
		if(event.getDatacontenttype() != null) {
			datacontenttypeFilter = CriteriaCondition.or(CriteriaCondition.eq("datacontenttype", null), CriteriaCondition.eq("datacontenttype", event.getDatacontenttype()));
		}
		else {
			datacontenttypeFilter = CriteriaCondition.eq("datacontenttype", null);
		}
		CriteriaCondition dataschemaFilter;
		if(event.getDataschema() != null) {
			dataschemaFilter = CriteriaCondition.or(CriteriaCondition.eq("dataschema", null), CriteriaCondition.eq("dataschema", event.getDataschema()));
		}
		else {
			dataschemaFilter = CriteriaCondition.eq("dataschema", null);
		}
		CriteriaCondition subjectFilter;
		if(event.getSubject() != null) {
			subjectFilter = CriteriaCondition.or(CriteriaCondition.eq("subject", null), CriteriaCondition.eq("subject", event.getSubject()));
		}
		else {
			subjectFilter = CriteriaCondition.eq("subject", null);
		}
		CriteriaCondition timeFilter;
		if(event.getTime() != null) {
			timeFilter = CriteriaCondition.or(CriteriaCondition.eq("time", null), CriteriaCondition.eq("time", event.getTime()));
		}
		else {
			timeFilter = CriteriaCondition.eq("time", null);
		}
		return CriteriaCondition.and(idFilter, sourceFilter, typeFilter, datacontenttypeFilter, dataschemaFilter, subjectFilter, timeFilter);
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
			if("id".equals(property)) {
				eventFilter.getWith().setId((String)value);
			}
			else if("source".equals(property)) {
				eventFilter.getWith().setSource((String)value);
			}
			else if("type".equals(property)) {
				eventFilter.getWith().setType((String)value);
			}
			else if("datacontenttype".equals(property)) {
				eventFilter.getWith().setDatacontenttype(value != null ? (String)value : null);
			}
			else if("dataschema".equals(property)) {
				eventFilter.getWith().setDataschema(value != null ? (String)value : null);
			}
			else if("subject".equals(property)) {
				eventFilter.getWith().setSubject(value != null ? (String)value : null);
			}
			else if("time".equals(property)) {
				eventFilter.getWith().setTime(value != null ? (String)value : null);
			}
			else if(!"specversion".equals(property)) {
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
