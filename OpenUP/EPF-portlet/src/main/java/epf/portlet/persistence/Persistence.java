/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.schema.Attribute;
import epf.client.schema.AttributeType;
import epf.client.schema.Entity;
import epf.client.security.Token;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Name;
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
@Named(Name.PERSISTENCE)
public class Persistence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Persistence.class.getName());
	
	/**
	 * 
	 */
	private Entity entity;
	
	/**
	 * 
	 */
	private List<Map<String, Object>> objects;
	
	/**
	 * 
	 */
	private List<String> fieldNames;
	
	/**
	 * 
	 */
	private List<String> fieldIds;
	
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
	private transient SessionUtil sessionUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		entity = eventUtil.getEvent(Event.SCHEMA_ENTITY);
		if(entity != null) {
			fieldNames = entity
					.getAttributes()
					.stream()
					.filter(
							attr -> !attr.isAssociation() 
							&& !attr.isCollection() 
							&& attr.getAttributeType() == AttributeType.BASIC
							)
					.map(Attribute::getName)
					.collect(Collectors.toList());
			Collections.sort(fieldNames, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return Integer.compare(o1.length(), o2.length());
				}}
			);
			fieldIds = fieldNames
					.stream()
					.map(field -> entity.getType() + "." + field)
					.collect(Collectors.toList());
		}
		final Token token = sessionUtil.getAttribute(Name.SECURITY_TOKEN);
		if(entity != null && token != null) {
			try(Client client = clientUtil.newClient(registryUtil.get("persistence"))){
				client.authorization(token.getRawToken());
				try(Response response = epf.client.persistence.Queries.executeQuery(
						client,
						EPF.SCHEMA, 
						path -> path.path(entity.getName()), 
						0, 
						100)){
					objects = response.readEntity(new GenericType<List<Map<String, Object>>>() {});
				}
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "postConstruct", e);
			}
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public List<Map<String, Object>> getObjects() {
		return objects;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public List<String> getFieldIds() {
		return fieldIds;
	}
}
