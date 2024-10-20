package epf.workflow.schema;

import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import jakarta.nosql.Column;

@Embeddable
public class ErrorReference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	@Description("The name of the error definition to reference. If set, all other properties are ignored.")
	private String refName;
	
	@Column
	@Description("An RFC6901 JSON pointer that precisely identifies the component within a workflow definition from which the error to reference originates")
	private String instance;
	
	@Column
	@Description("A RFC3986 URI reference that identifies the type of error(s) to reference")
	private String type;
	
	@Column
	@Description("The status code of the error(s) to reference")
	private String status;

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
