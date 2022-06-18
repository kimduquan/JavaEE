package epf.schema.utility;

import java.io.Serializable;
import javax.persistence.Transient;

/**
 * 
 */
public class EPFEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@Transient
	private String tenant;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(final String tenant) {
		this.tenant = tenant;
	}
}
