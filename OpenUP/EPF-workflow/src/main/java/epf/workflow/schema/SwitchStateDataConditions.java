package epf.workflow.schema;

import javax.validation.constraints.NotNull;

/**
 * @author PC
 *
 */
public class SwitchStateDataConditions extends SwitchStateConditions {
	/**
	 * 
	 */
	@NotNull
	private String condition;
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
