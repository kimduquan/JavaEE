/**
 * 
 */
package epf.portlet.schema;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.schema.Entity;
import epf.client.security.Token;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Naming;
import epf.portlet.Parameter;
import epf.portlet.ParameterUtil;
import epf.portlet.SessionUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.schema.EPF;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Naming.SCHEMA)
public class Schema {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Schema.class.getName());
	
	/**
	 * 
	 */
	private Map<String, Entity> entities;
	
	/**
	 * 
	 */
	private List<Map.Entry<String, Entity>> sortedEntities;
	
	/**
	 * 
	 */
	private String entity;
	
	/**
	 * 
	 */
	@Inject
	private transient SessionUtil sessionUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient RegistryUtil registryUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient EventUtil eventUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ParameterUtil paramUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final List<Entity> entityList = getEntities(EPF.SCHEMA);
			if(entityList != null) {
				entities = entityList
						.stream()
						.collect(Collectors.toMap(
								Entity::getName, 
								Function.identity())
								);
				sortedEntities = sort(entities.entrySet());
			}
			entity = paramUtil.getValue(Parameter.SCHEMA_ENTITY);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	protected List<Entity> getEntities(final String unit) throws Exception{
		final Token token = sessionUtil.getAttribute(Naming.SECURITY_TOKEN);
		if(token != null) {
			final URI schemaUrl = registryUtil.get("schema");
			try(Client client = clientUtil.newClient(schemaUrl)){
				client.authorization(token.getRawToken());
				try(Response response = epf.client.schema.Schema.getEntities(client, unit)){
					return response.readEntity(new GenericType<List<Entity>>() {});
				}
			}
		}
		return null;
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Entity> getEntities(){
		return entities;
	}

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(final String entity) {
		this.entity = entity;
	}
	
	/**
	 * 
	 */
	public void handleEntityChanged(final ValueChangeEvent event) {
		entity = event.getNewValue().toString();
		final Entity selectedEntity = entities.get(entity);
		if(selectedEntity != null) {
			eventUtil.setEvent(Event.SCHEMA_ENTITY, selectedEntity);
		}
		paramUtil.setValue("entity", entity);
	}
	
	/**
	 * @param entities
	 * @return
	 */
	protected static List<Map.Entry<String, Entity>> sort(final Set<Map.Entry<String, Entity>> entities){
		return entities
		.stream()
		.sorted(new Comparator<Map.Entry<String, Entity>>() {
			@Override
			public int compare(Entry<String, Entity> o1, Entry<String, Entity> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().getType().compareTo(o2.getValue().getType());
			}}
		)
		.collect(Collectors.toList());
	}

	public List<Map.Entry<String, Entity>> getSortedEntities() {
		return sortedEntities;
	}
}
