package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class FunctionDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String name;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private String operation;
	
	/**
	 * 
	 */
	@Column
	private FunctionType type = FunctionType.rest;
	
	/**
	 * 
	 */
	@Column
	private String authRef;
	
	/**
	 * 
	 */
	@Column
	private Object metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public FunctionType getType() {
		return type;
	}

	public void setType(FunctionType type) {
		this.type = type;
	}

	public String getAuthRef() {
		return authRef;
	}

	public void setAuthRef(String authRef) {
		this.authRef = authRef;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
