package epf.function.persistence;

import javax.ws.rs.core.Link;
import epf.function.Function;

/**
 * @author PC
 *
 */
public class EntityFunction extends Function {
	
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
	private String id;

	/**
	 * @param service
	 * @param method
	 * @param path
	 */
	public EntityFunction(final String service, final String method, final String path) {
		super(service, method, path);
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

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build(schema, entity, id);
	}

}
