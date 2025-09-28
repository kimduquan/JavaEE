package epf.schema.utility;

import java.io.Serializable;
import jakarta.persistence.Transient;

public class EPFEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private String organization;

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
}
