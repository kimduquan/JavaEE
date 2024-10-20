package epf.workflow.schema.state;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class SwitchStateDataConditions extends SwitchStateConditions {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Workflow expression evaluated against state data. Must evaluate to true or false")
	private String condition;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
