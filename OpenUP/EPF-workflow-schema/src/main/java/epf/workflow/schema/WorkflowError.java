package epf.workflow.schema;

import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import jakarta.nosql.Column;

/**
 * @author PC
 *
 */
@Embeddable
public class WorkflowError implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String code;
	
	/**
	 * 
	 */
	@Column
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @param error
	 * @return
	 */
	public boolean equals(final WorkflowError error) {
		return this.name.equals(error.name) && this.code.equals(error.code);
	}
}
