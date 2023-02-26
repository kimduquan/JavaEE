package epf.workflow.schema;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;

import epf.workflow.schedule.schema.adapter.ScheduleDefinitionAdapter;
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
	@JsonbTypeAdapter(value = ScheduleDefinitionAdapter.class)
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
