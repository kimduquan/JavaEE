package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class FunctionDefinition {

	private String name;
	
	private String operation;
	
	private Type type = Type.rest;
	
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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
