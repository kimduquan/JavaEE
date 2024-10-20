package epf.workflow.schema.error;

import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import epf.workflow.schema.error.adapter.BooleanOrErrorThrowDefinitionAdapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;

@Embeddable
public class ErrorOutcomeDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@Description("If true, ends the workflow.")
	@JsonbTypeAdapter(value = BooleanOrEndDefinitionAdapter.class)
	private BooleanOrObject<EndDefinition> end;
	
	@Column
	@Description("Indicates that the workflow should transition to the specified state when the error is handled. All potential other activities are terminated.	")
	@JsonbTypeAdapter(value = StringOrTransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	
	@Column
	@Description("Indicates that the handled error should be rethrown. If true, the error is re-thrown as is. Otherwise, configures the error to throw, potentially using runtime expressions.")
	@JsonbTypeAdapter(value = BooleanOrErrorThrowDefinitionAdapter.class)
	private BooleanOrObject<ErrorThrowDefinition> throw_;

	public BooleanOrObject<EndDefinition> getEnd() {
		return end;
	}

	public void setEnd(BooleanOrObject<EndDefinition> end) {
		this.end = end;
	}

	public StringOrObject<TransitionDefinition> getTransition() {
		return transition;
	}

	public void setTransition(StringOrObject<TransitionDefinition> transition) {
		this.transition = transition;
	}

	public BooleanOrObject<ErrorThrowDefinition> getThrow_() {
		return throw_;
	}

	public void setThrow_(BooleanOrObject<ErrorThrowDefinition> throw_) {
		this.throw_ = throw_;
	}
}
