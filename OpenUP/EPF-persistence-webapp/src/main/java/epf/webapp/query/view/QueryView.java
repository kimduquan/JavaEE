package epf.webapp.query.view;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import epf.persistence.schema.Attribute;
import epf.persistence.schema.AttributeType;
import epf.persistence.schema.Entity;
import epf.util.json.JsonUtil;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.Session;
import epf.webapp.naming.Naming;
import epf.webapp.query.QueryCollector;
import epf.webapp.schema.SchemaCache;

/**
 * 
 */
@ViewScoped
@Named(Naming.Query.VIEW)
public class QueryView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private String schema;
	
	/**
	 *
	 */
	private String entity;
	
	/**
	 *
	 */
	private List<Attribute> attributes;
	
	/**
	 *
	 */
	private QueryCollector collector;
	
	/**
	 *
	 */
	@Inject
	private transient SchemaCache schemaCache;
	
	/**
	 *
	 */
	@Inject
	private transient GatewayUtil gateway;
	
	/**
	 *
	 */
	@Inject
	private transient Session session;
	
	/**
	 *
	 */
	@Inject
	private HttpServletRequest request;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void initialize() {
		schema = request.getParameter("schema");
		entity = request.getParameter("entity");
		final Optional<Entity> currentEntity = schemaCache.getEntity(schema, entity);
		if(currentEntity.isPresent()) {
			attributes = currentEntity.get().getAttributes().stream().filter(
					attribute -> !attribute.isAssociation() && !attribute.isCollection() && AttributeType.BASIC.equals(attribute.getAttributeType()))
					.collect(Collectors.toList());
			collector = new QueryCollector(gateway, session.getToken(), schema, currentEntity.get());
		}
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(final String schema) {
		this.schema = schema;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(final String entity) {
		this.entity = entity;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public QueryCollector getCollector() {
		return collector;
	}
	
	/**
	 * @param object
	 * @param attribute
	 * @return
	 */
	public String getAttribute(final JsonObject object, final String attribute) {
		return JsonUtil.getString(object, attribute, "");
	}
	
	/**
	 * 
	 */
	public void remove() {
		
	}
}
