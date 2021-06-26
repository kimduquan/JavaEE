/**
 * 
 */
package epf.portlet.persistence;

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
import epf.portlet.Naming;
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
@Named(Naming.PERSISTENCE)
public class Persistence {
	
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
	private List<Attribute> attributes;
	
	/**
	 * 
	 */
	private List<Map<String, Object>> objects;
	
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
			attributes = entity
					.getAttributes()
					.stream()
					.filter(
							attr -> !attr.isAssociation() 
							&& !attr.isCollection() 
							&& attr.getAttributeType().equals(AttributeType.BASIC)
							)
					.sorted(new Comparator<Attribute>() {
						@Override
						public int compare(Attribute o1, Attribute o2) {
							int result = Integer.compare(o1.getName().length(), o2.getName().length());
							if(result == 0) {
								result = o1.getName().compareTo(o2.getName());
							}
							return result;
						}}
					)
					.collect(Collectors.toList());
		}
		final Token token = sessionUtil.getAttribute(Naming.SECURITY_TOKEN);
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

	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * @param attribute
	 * @return
	 */
	public String getId(final Attribute attribute) {
		return entity.getType() + "." + attribute.getName();
	}
}
