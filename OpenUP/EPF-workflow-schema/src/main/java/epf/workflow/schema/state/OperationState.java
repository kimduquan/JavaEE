package epf.workflow.schema.state;

import java.util.List;
import java.util.Map;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import epf.workflow.schema.error.ErrorDefinition;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
@DiscriminatorValue(Type.OPERATION)
public class OperationState extends State {

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
	@Description("Should actions be performed sequentially or in parallel.")
	@DefaultValue("sequential")
	private Mode actionMode = Mode.sequential;
	
	@NotNull
	@Column
	@Description("Actions to be performed")
	private List<ActionDefinition> actions;
	
	@Column
	@Description("State specific timeout settings")
	private WorkflowTimeoutDefinition timeouts;
	
	@Column
	@Description("State data filter")
	private StateDataFilter stateDataFilter;
	
	@Column
	@Description("States error handling and retries definitions")
	private List<ErrorDefinition> onErrors;
	
	@Column
	@Description("Next transition of the workflow after all the actions have been performed")
	@JsonbTypeAdapter(value = StringOrTransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	
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
	
	@Column
	@Description("Is this state an end state")
	@JsonbTypeAdapter(value = BooleanOrEndDefinitionAdapter.class)
	private BooleanOrObject<EndDefinition> end;

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

	public Mode getActionMode() {
		return actionMode;
	}

	public void setActionMode(Mode actionMode) {
		this.actionMode = actionMode;
	}

	public List<ActionDefinition> getActions() {
		return actions;
	}

	public void setActions(List<ActionDefinition> actions) {
		this.actions = actions;
	}

	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
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

	public StringOrObject<TransitionDefinition> getTransition() {
		return transition;
	}

	public void setTransition(StringOrObject<TransitionDefinition> transition) {
		this.transition = transition;
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

	public BooleanOrObject<EndDefinition> getEnd() {
		return end;
	}

	public void setEnd(BooleanOrObject<EndDefinition> end) {
		this.end = end;
	}
}
