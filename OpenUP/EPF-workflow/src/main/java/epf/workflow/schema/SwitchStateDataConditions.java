package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class SwitchStateDataConditions extends SwitchStateConditions {
	/**
	 * 
	 */
	@NotNull
	@Column
	private String condition;
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
