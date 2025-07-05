package epf.workflow.schema.state;

import java.util.List;
import java.util.Map;
import jakarta.nosql.DiscriminatorValue;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.TransitionOrEnd;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.adapter.TransitionOrEndAdapter;
import epf.workflow.schema.error.ErrorDefinition;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
@DiscriminatorValue(Type.SWITCH)
public class SwitchState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Unique State name. Must follow the Serverless Workflow Naming Convention")
	private String name;
	
	@NotNull
	@Column
	@Description("State type")
	private String type;
	
	@Column
	@Description("Defined if the Switch state evaluates conditions and transitions based on state data.")
	private List<SwitchStateDataConditions> dataConditions;
	
	@Column
	@Description("Defined if the Switch state evaluates conditions and transitions based on arrival of events.")
	private List<SwitchStateEventConditions> eventConditions;
	
	@Column
	@Description("State data filter")
	private StateDataFilter stateDataFilter;
	
	@Column
	@Description("States error handling and retries definitions")
	private List<ErrorDefinition> onErrors;
	
	@Column
	@Description("State specific timeout settings")
	private WorkflowTimeoutDefinition timeouts;
	
	@Column
	@Description("Default transition of the workflow if there is no matching data conditions or event timeout is reached. Can be a transition or end definition")
	@JsonbTypeAdapter(value = TransitionOrEndAdapter.class)
	private TransitionOrEnd defaultCondition;
	
	@Column
	@Description("Unique name of a workflow state which is responsible for compensation of this state")
	private String compensatedBy;
	
	@Column
	@Description("If true, this state is used to compensate another state.")
	@DefaultValue("false")
	private Boolean usedForCompensation = false;
	
	@Column
	@Description("Metadata information")
	private Map<String, String> metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SwitchStateDataConditions> getDataConditions() {
		return dataConditions;
	}

	public void setDataConditions(List<SwitchStateDataConditions> dataConditions) {
		this.dataConditions = dataConditions;
	}

	public List<SwitchStateEventConditions> getEventConditions() {
		return eventConditions;
	}

	public void setEventConditions(List<SwitchStateEventConditions> eventConditions) {
		this.eventConditions = eventConditions;
	}

	public StateDataFilter getStateDataFilter() {
		return stateDataFilter;
	}

	public void setStateDataFilter(StateDataFilter stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}

	public List<ErrorDefinition> getOnErrors() {
		return onErrors;
	}

	public void setOnErrors(List<ErrorDefinition> onErrors) {
		this.onErrors = onErrors;
	}

	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}

	public TransitionOrEnd getDefaultCondition() {
		return defaultCondition;
	}

	public void setDefaultCondition(TransitionOrEnd defaultCondition) {
		this.defaultCondition = defaultCondition;
	}

	public String getCompensatedBy() {
		return compensatedBy;
	}

	public void setCompensatedBy(String compensatedBy) {
		this.compensatedBy = compensatedBy;
	}

	public Boolean getUsedForCompensation() {
		return usedForCompensation;
	}

	public void setUsedForCompensation(Boolean usedForCompensation) {
		this.usedForCompensation = usedForCompensation;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
