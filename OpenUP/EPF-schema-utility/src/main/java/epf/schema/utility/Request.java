package epf.schema.utility;

import jakarta.enterprise.context.RequestScoped;

/**
 * @author PC
 *
 */
@RequestScoped
public class Request {

	/**
	 * 
	 */
	private String tenant;
	
	/**
	 * 
	 */
	private String schema;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(final String tenant) {
		this.tenant = tenant;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(final String schema) {
		this.schema = schema;
	}
}
