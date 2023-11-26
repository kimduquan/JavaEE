package epf.workflow.schema;

import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import jakarta.nosql.Column;

/**
 * 
 */
@Embeddable
public class Extension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column
	private String extensionId;
	
	/**
	 * 
	 */
	@Column
	private String path;
}
