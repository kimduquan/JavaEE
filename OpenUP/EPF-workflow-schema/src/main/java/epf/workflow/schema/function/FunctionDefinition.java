package epf.workflow.schema.function;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.function.mapping.FunctionTypeConverter;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.Map;
import org.eclipse.jnosql.mapping.Convert;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class FunctionDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	@Convert(value = FunctionTypeConverter.class)
	private FunctionType type = FunctionType.rest;
	
	/**
	 * 
	 */
	@Column
	private String authRef;
	
	/**
	 * 
	 */
	private Map<String, String> metadata;

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

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
