package epf.workflow.schema;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import epf.workflow.schema.schedule.ScheduleDefinition;
import epf.workflow.schema.schedule.adapter.ScheduleDefinitionAdapter;
import epf.workflow.schema.util.Either;

/**
 * @author PC
 *
 */
@Embeddable
public class StartDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private Either<String, ScheduleDefinition> schedule;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Either<String, ScheduleDefinition> getSchedule() {
		return schedule;
	}

	public void setSchedule(Either<String, ScheduleDefinition> schedule) {
		this.schedule = schedule;
	}
}
