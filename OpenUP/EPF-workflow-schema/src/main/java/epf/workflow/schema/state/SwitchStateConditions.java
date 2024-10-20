package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import org.eclipse.jnosql.mapping.MappedSuperclass;
import org.eclipse.microprofile.graphql.Description;

@MappedSuperclass
public class SwitchStateConditions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Data condition name. Must follow the Serverless Workflow Naming Convention")
	private String name;
	
	@Column
	@Description("Transition to another state if condition is true")
	@JsonbTypeAdapter(value = StringOrTransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	
	@Column
	@Description("End workflow execution if condition is true")
	@JsonbTypeAdapter(value = BooleanOrEndDefinitionAdapter.class)
	private BooleanOrObject<EndDefinition> end;
	
	@Column
	@Description("Metadata information")
	private Map<String, String> metadata;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public StringOrObject<TransitionDefinition> getTransition() {
		return transition;
	}
	public void setTransition(StringOrObject<TransitionDefinition> transition) {
		this.transition = transition;
	}
	public BooleanOrObject<EndDefinition> getEnd() {
		return end;
	}
	public void setEnd(BooleanOrObject<EndDefinition> end) {
		this.end = end;
	}
	public Map<String, String> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

}
