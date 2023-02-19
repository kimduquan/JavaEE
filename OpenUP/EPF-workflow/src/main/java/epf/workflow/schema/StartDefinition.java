package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class StartDefinition {

	/**
	 * 
	 */
	@Column
	private String stateName;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private Object schedule;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Object getSchedule() {
		return schedule;
	}

	public void setSchedule(Object schedule) {
		this.schedule = schedule;
	}
}
