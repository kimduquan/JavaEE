package epf.workflow;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.concurrent.client.ext.Concurrent;
import epf.concurrent.client.ext.Synchronized;
import epf.event.schema.Event;
import epf.naming.Naming;
import epf.nosql.schema.EitherUtil;
import epf.util.MapUtil;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import epf.workflow.client.Internal;
import epf.workflow.expressions.WorkflowExpressions;
import epf.workflow.model.WorkflowData;
import epf.workflow.schema.CorrelationDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.EventRefDefinition;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.event.ProducedEventDefinition;
import epf.workflow.schema.function.Invoke;
import epf.workflow.schema.state.EventState;
import epf.workflow.states.WorkflowStates;
import epf.workflow.util.LinkBuilder;
import epf.workflow.util.ResponseBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;
import jakarta.ws.rs.core.Link;

/**
 * 
 */
@ApplicationScoped
public class WorkflowEvents {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(WorkflowEvents.class.getName());
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowExpressions workflowExpressions;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowStates workflowStates;
	
	/**
	 * 
	 */
	@Inject
	transient Concurrent concurrent;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Concurrent.CONCURRENT_URL)
	transient URI concurrentUrl;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			concurrent.connectToServer(concurrentUrl);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "postConstruct", e);
		}
	}
	
	public Map<String, EventDefinition> getEvents(final WorkflowDefinition workflowDefinition){
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		final Map<String, EventDefinition> map = new HashMap<>();
		return MapUtil.putAll(map, events.iterator(), EventDefinition::getName);
	}
	
	public EventDefinition getEventDefinition(final WorkflowDefinition workflowDefinition, final String event) {
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		return events.stream().filter(eventDef -> eventDef.getName().equals(event)).findFirst().get();
	}
	
	public Event newEvent(final EventDefinition eventDefinition, final URI instance, final Map<String, Object> ext) {
		final Event event = new Event();
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		event.setSubject(instance.toString());
		eventDefinition.getCorrelation().forEach(correlationDefinition -> {
			ext.put(correlationDefinition.getContextAttributeName(), correlationDefinition.getContextAttributeValue());
		});
		return event;
	}
	
	private Map<String, Object> eventData(final EventRefDefinition eventRefDefinition, final EventDefinition eventRef, final URI instance, final WorkflowData workflowData) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = newEvent(eventRef, instance, ext);
		Object data = null;
		if(eventRefDefinition.getData().isLeft()) {
			final JsonValue value = workflowExpressions.getValue(eventRefDefinition.getData().getLeft(), workflowData.getInput());
			data = JsonUtil.asValue(value);
		}
		else if(eventRefDefinition.getData().isRight()) {
			data = eventRefDefinition.getData().getRight();
		}
		event.setData(data);
		Map<String, Object> contextAttributes = eventRefDefinition.getContextAttributes();
		if(contextAttributes == null) {
			contextAttributes = new HashMap<>();
		}
		return event.toMap(contextAttributes);
	}
	
	public ResponseBuilder eventRef(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final String state, final ActionDefinition actionDefinition, final EventRefDefinition eventRefDefinition, final URI instance, final WorkflowData workflowData) throws Exception {
		final List<Map<String, Object>> eventDatas = new ArrayList<>();
		final List<Link> eventLinks = new ArrayList<>();
		final Link producesLink = epf.event.client.Event.producesLink();
		final Link consumeLink = Internal.observesLink(workflowDefinition.getId(), state, Optional.ofNullable(workflowDefinition.getVersion()));
		final LinkBuilder builder = new LinkBuilder();
		
		final EventDefinition produceEventDefinition = getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getProduceEventRef());
		final Map<String, Object> produceEventData = eventData(eventRefDefinition, produceEventDefinition, instance, workflowData);
		final Link produceEventLink = builder.link(producesLink).at(response.getSize()).build();
		eventDatas.add(produceEventData);
		eventLinks.add(produceEventLink);
		
		if(actionDefinition.getEventRef().getConsumeEventRef() != null) {
			final EventDefinition consumeEventDefinition = getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getConsumeEventRef());
			final Map<String, Object> consumeEventData = eventData(eventRefDefinition, consumeEventDefinition, instance, workflowData);
			builder.link(consumeLink);
			if(Invoke.sync == eventRefDefinition.getInvoke()) {
				final Synchronized synchronized_ = concurrent.synchronized_();
				String timeout = eventRefDefinition.getConsumeEventTimeout();
				if(timeout == null) {
					final WorkflowTimeoutDefinition timeouts = EitherUtil.getObject(workflowDefinition.getTimeouts());
					timeout = timeouts.getActionExecTimeout();
				}
				if(timeout != null) {
					builder.synchronized_(synchronized_.getId(), Duration.parse(timeout));
				}
				else {
					builder.synchronized_(synchronized_.getId());
				}
			}
			final Link consumeEventLink = builder.at(response.getSize()).build();
			
			eventDatas.add(consumeEventData);
			eventLinks.add(consumeEventLink);
		}
		return workflowStates.partial(response, eventDatas, eventLinks.toArray(new Link[0]));
	}
	
	private Map<String, Object> produceEventData(final ProducedEventDefinition producedEventDefinition, final EventDefinition eventDefinition, final URI instance, final WorkflowData workflowData) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = newEvent(eventDefinition, instance, ext);
		Object data = null;
		if(producedEventDefinition.getData().isLeft()) {
			final JsonValue value = workflowExpressions.getValue(producedEventDefinition.getData().getLeft(), workflowData.getInput());
			data = JsonUtil.asValue(value);
		}
		else if(producedEventDefinition.getData().isRight()) {
			data = producedEventDefinition.getData().getRight();
		}
		event.setData(data);
		Map<String, Object> contextAttributes = producedEventDefinition.getContextAttributes();
		if(contextAttributes == null) {
			contextAttributes = new HashMap<>();
		}
		return event.toMap(contextAttributes);
	}
	
	public ResponseBuilder produceEvents(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final List<ProducedEventDefinition> produceEvents, final URI instance, final WorkflowData workflowData) throws Exception {
		final Map<String, EventDefinition> eventDefinitions = new HashMap<>();
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		MapUtil.putAll(eventDefinitions, events.iterator(), EventDefinition::getName);
		final List<Map<String, Object>> eventDatas = new ArrayList<>();
		final List<Link> producesLinks = new ArrayList<>();
		final Link producesLink = epf.event.client.Event.producesLink();
		final LinkBuilder builder = new LinkBuilder();
		for(ProducedEventDefinition producedEventDefinition : produceEvents) {
			final EventDefinition eventDefinition = eventDefinitions.get(producedEventDefinition.getEventRef());
			final Map<String, Object> eventData = produceEventData(producedEventDefinition, eventDefinition, instance, workflowData);
			eventDatas.add(eventData);
			builder.link(producesLink).at(response.getSize());
			producesLinks.add(builder.build());
		}
		return workflowStates.partial(response, eventDatas, producesLinks.toArray(new Link[0]));
	}
	
	public List<Map<String, Object>> events(final EventState eventState, final Map<String, EventDefinition> eventDefinitions, final URI instance) {
		final List<Map<String, Object>> events = new ArrayList<>();
		for(OnEventsDefinition onEventsDefinition : eventState.getOnEvents()) {
			for(String eventRef : onEventsDefinition.getEventRefs()) {
				final EventDefinition eventDefinition = eventDefinitions.get(eventRef);
				final Map<String, Object> map = new HashMap<>();
				final epf.event.schema.Event event = newEvent(eventDefinition, instance, map);
				map.put(epf.naming.Naming.Event.Schema.SOURCE, event.getSource());
				map.put(epf.naming.Naming.Event.Schema.SUBJECT, event.getSubject());
				map.put(epf.naming.Naming.Event.Schema.TYPE, event.getType());
				events.add(map);
			}
		}
		return events;
	}
	
	private boolean onEvent(final EventDefinition eventDefinition, final List<Map<String, Object>> eventDatas, final List<epf.event.schema.Event> events) throws Exception {
		boolean equals = true;
		final Iterator<Map<String, Object>> eventData = eventDatas.iterator();
		while(eventData.hasNext()) {
			equals = true;
			final Map<String, Object> ext = new HashMap<>();
			final Event event = Event.event(eventData.next(), ext);
			if(equals && eventDefinition.getSource() != null) {
				equals = eventDefinition.getSource().equals(event.getSource());
			}
			if(equals && eventDefinition.getType() != null) {
				equals = eventDefinition.getType().equals(event.getType());
			}
			if(equals && eventDefinition.getCorrelation() != null) {
				for(CorrelationDefinition correlationDefinition : eventDefinition.getCorrelation()) {
					final Object contextAttributeValue = ext.get(correlationDefinition.getContextAttributeName());
					if(correlationDefinition.getContextAttributeValue() != null) {
						equals = correlationDefinition.getContextAttributeValue().equals(contextAttributeValue);
					}
					if(!equals) {
						break;
					}
				}
			}
			if(equals) {
				events.add(event);
				break;
			}
		}
		return equals;
	}
	
	public boolean onEvents(final WorkflowDefinition workflowDefinition, final EventState eventState, final OnEventsDefinition onEventsDefinition, final Map<String, EventDefinition> eventDefinitions, final List<Map<String, Object>> eventDatas, final List<epf.event.schema.Event> events) throws Exception {
		final List<EventDefinition> onEvents = MapUtil.getAll(eventDefinitions, onEventsDefinition.getEventRefs().iterator());
		boolean all = true;
		boolean any = false;
		for(EventDefinition eventDefinition : onEvents) {
			if(onEvent(eventDefinition, eventDatas, events)) {
				any = true;
			}
			else {
				all = false;
			}
		}
		final boolean exclusive = !Boolean.FALSE.equals(eventState.isExclusive());
		return exclusive ? any : all;
	}
}
