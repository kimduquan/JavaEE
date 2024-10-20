package epf.workflow.schema.state;

import java.util.Map;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
@DiscriminatorValue(Type.INJECT)
public class InjectState extends State {

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
	
	@NotNull
	@Column
	@Description("SON object which can be set as state's data input and can be manipulated via filter")
	private Map<String, Object> data;
	
	@Column
	@Description("State data filter")
	private StateDataFilter stateDataFilter;
	
	@Column
	@Description("Next transition of the workflow after injection has completed")
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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public StateDataFilter getStateDataFilter() {
		return stateDataFilter;
	}

	public void setStateDataFilter(StateDataFilter stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
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
