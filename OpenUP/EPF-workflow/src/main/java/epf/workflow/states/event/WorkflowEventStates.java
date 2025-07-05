package epf.workflow.states.event;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import epf.event.schema.Event;
import epf.nosql.schema.EitherUtil;
import epf.util.MapUtil;
import epf.workflow.WorkflowEvents;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.state.EventState;
import epf.workflow.states.WorkflowStates;
import epf.workflow.util.LinkBuilder;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Link;

@ApplicationScoped
public class WorkflowEventStates {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowEvents workflowEvents;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowStates workflowStates;
	
	public ResponseBuilder transitionEventState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final EventState eventState, final URI instance) throws Exception {
		final Map<String, EventDefinition> eventDefs = new HashMap<>();
		final List<EventDefinition> eventDefinitions = EitherUtil.getArray(workflowDefinition.getEvents());
		MapUtil.putAll(eventDefs, eventDefinitions.iterator(), EventDefinition::getName);
		final List<Map<String, Object>> events = new ArrayList<>();
		final List<Link> eventLinks = new ArrayList<>();
		final Link producesLink = epf.event.client.Event.producesLink();
		final LinkBuilder builder = new LinkBuilder();
		for(OnEventsDefinition onEventsDef : eventState.getOnEvents()) {
			for(String eventRef : onEventsDef.getEventRefs()) {
				final EventDefinition eventDef = eventDefs.get(eventRef);
				final Map<String, Object> ext = new HashMap<>();
				final Event event = workflowEvents.newEvent(eventDef, instance, ext);
				Link eventLink = builder.link(producesLink).at(response.getSize()).build();
				if(eventLink != null) {
					final Map<String, Object> map = event.toMap(ext);
					events.add(map);
					eventLinks.add(eventLink);
				}
			}
		}
		return workflowStates.partial(response, events, eventLinks.toArray(new Link[0]));
	}
}
