package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class FunctionDefinition {

	private String name;
	
	private String operation;
	
	private FunctionType type = FunctionType.rest;
	
	private String authRef;
	
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
