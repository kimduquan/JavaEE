package epf.workflow.schema.interfaces;

import java.io.Serializable;

/**
 * @author PC
 *
 */
public class Extension implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String extensionId;

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(final String extensionId) {
		this.extensionId = extensionId;
	}
}
