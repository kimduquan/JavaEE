package epf.workflow.schema;

import java.io.Serializable;
import jakarta.nosql.Embeddable;
import jakarta.nosql.Column;

@Embeddable
public class Extension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String extensionId;

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}
}
