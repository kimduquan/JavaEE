package epf.workflow.schema;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.schedule.ScheduleDefinition;
import epf.workflow.schema.schedule.adapter.StringOrScheduleDefinitionAdapter;

@Embeddable
public class StartDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@Description("Name of the starting workflow state. Must follow the Serverless Workflow Naming Convention")
	private String stateName;
	
	@NotNull
	@Column
	@Description("Define the recurring time intervals or cron expressions at which workflow instances should be automatically started.")
	@JsonbTypeAdapter(value = StringOrScheduleDefinitionAdapter.class)
	private StringOrObject<ScheduleDefinition> schedule;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public StringOrObject<ScheduleDefinition> getSchedule() {
		return schedule;
	}

	public void setSchedule(StringOrObject<ScheduleDefinition> schedule) {
		this.schedule = schedule;
	}
}
