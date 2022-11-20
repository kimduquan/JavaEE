package epf.function.persistence;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class PersistFunction extends Function {

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
	public PersistFunction() {
		super(Naming.PERSISTENCE, HttpMethod.POST, "{schema}/{entity}");
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
	
	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build(schema, entity);
	}
}
